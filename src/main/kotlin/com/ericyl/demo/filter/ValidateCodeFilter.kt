package com.ericyl.demo.filter

import com.ericyl.demo.common.REDIS_VALIDATE_IMAGE_CODE_KEY
import com.ericyl.demo.common.ValidateCodeType
import com.ericyl.demo.exception.ValidateCodeException
import com.ericyl.demo.properties.DemoProperties
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.util.AntPathMatcher
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.ServletRequestUtils
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class ValidateCodeFilter(private val authenticationFailureHandler: AuthenticationFailureHandler? = null,
                         private val redisTemplate: StringRedisTemplate? = null,
                         private val properties:  DemoProperties? = null) : OncePerRequestFilter() {

    private val antPathMatcher = AntPathMatcher()

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val header = request.getHeader("Authorization")

        if (header == null || !header.toLowerCase().startsWith("basic ")) {

            val type = getValidateCodeType(request)
            if (type != null) {
                try {
                    validate(type, ServletWebRequest(request, response))
                } catch (exception: ValidateCodeException) {
                    authenticationFailureHandler?.onAuthenticationFailure(request, response, exception)
                    return
                }

            }
        }

        filterChain.doFilter(request, response)
    }

    private fun getValidateCodeType(request: HttpServletRequest): ValidateCodeType? {

        if (request.method.toLowerCase() != "get") {
            val imageUrls = properties?.validateCode?.image?.urls
            if (imageUrls != null && imageUrls.any { antPathMatcher.match(it, request.requestURI) })
                return ValidateCodeType.IMAGE

            val smsUrls = properties?.validateCode?.sms?.urls
            if (smsUrls != null && smsUrls.any { antPathMatcher.match(it, request.requestURI) })
                return ValidateCodeType.SMS
        }
        return null
    }


    private fun validate(validateCodeType: ValidateCodeType, request: ServletWebRequest) {
        var deviceId = request.getHeader("deviceId")
        if (deviceId?.isBlank() != false)
            deviceId = request.sessionId
        val codeInRedis = redisTemplate!!.opsForHash<String, String>().get(REDIS_VALIDATE_IMAGE_CODE_KEY, deviceId)

        val codeInRequest: String?
        try {
            when (validateCodeType) {
                ValidateCodeType.IMAGE -> codeInRequest = ServletRequestUtils.getStringParameter(request.request, "imageCode")
                ValidateCodeType.SMS -> codeInRequest = ServletRequestUtils.getStringParameter(request.request, "smsCode")
                else -> codeInRequest = null
            }
        } catch (e: ServletRequestBindingException) {
            throw ValidateCodeException("获取验证码的值失败")
        }

        if (codeInRequest.isNullOrBlank())
            throw ValidateCodeException("请填写验证码")

        if (codeInRedis.isNullOrBlank())
            throw ValidateCodeException("验证码已过期， 请从新获取")

        if (codeInRedis != codeInRequest)
            throw ValidateCodeException("验证码不正确")

        redisTemplate.opsForHash<String, String>().delete(REDIS_VALIDATE_IMAGE_CODE_KEY, deviceId)

    }

}
