package com.ericyl.demo.controller

import com.ericyl.demo.common.CODE_AN_UNKNOWN_ERROR
import com.ericyl.demo.common.REDIS_VALIDATE_IMAGE_CODE_KEY
import com.ericyl.demo.exception.RestException
import com.ericyl.demo.validate.code.ValidateCodeGenerator
import com.ericyl.demo.validate.code.image.ImageCode
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.ServletWebRequest
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@EnableScheduling
@Controller
class MainController {

    private val requestCache = HttpSessionRequestCache()

    @Autowired
    private val validateCodeGenerator: ValidateCodeGenerator<ImageCode>? = null

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null

//    @Autowired
//    private val persistentTokenRepository: PersistentTokenRepository? = null

    @GetMapping(value = ["/", "index.html", "logout"])
    @PreAuthorize("hasPermission(#request, 'RbacPermission')")
    fun main(request: HttpServletRequest, model: Model, authentication: Authentication): String {

        return "index"
    }


    @GetMapping("signIn.html")
    fun login(request: HttpServletRequest, response: HttpServletResponse): String {
        requestCache.getRequest(request, response) ?: return "index"
        return "login"
    }


    @GetMapping("code/image")
    @Throws(IOException::class)
    fun image(request: HttpServletRequest, response: HttpServletResponse) {
        var deviceId = request.getHeader("deviceId")
        if (StringUtils.isBlank(deviceId))
            deviceId = request.session.id
        if (StringUtils.isBlank(deviceId))
            throw RestException(CODE_AN_UNKNOWN_ERROR, "无法获取浏览器的唯一值")

        if (BooleanUtils.isTrue(redisTemplate?.opsForHash<String, String>()?.hasKey(REDIS_VALIDATE_IMAGE_CODE_KEY, deviceId)))
            redisTemplate?.opsForHash<String, String>()?.delete(REDIS_VALIDATE_IMAGE_CODE_KEY, deviceId)
        val imageCode = validateCodeGenerator!!.generator(ServletWebRequest(request))
        redisTemplate?.opsForHash<String, String>()?.put(REDIS_VALIDATE_IMAGE_CODE_KEY, deviceId, imageCode.code!!)
        redisTemplate?.expire(REDIS_VALIDATE_IMAGE_CODE_KEY, imageCode.expiredSeconds!!.toLong(), TimeUnit.SECONDS)
        ImageIO.write(imageCode.image, "JPEG", response.outputStream)

    }


}