package com.ericyl.demo.config

import com.ericyl.demo.exception.UnAuthorizedException
import com.ericyl.demo.service.UserService
import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider : AuthenticationProvider {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    override fun authenticate(authentication: Authentication): Authentication {

        val user = userService?.findByUsername(authentication.name) ?: throw  UsernameNotFoundException("获取用户信息失败")

        if (!passwordEncoder!!.matches(authentication.credentials.toString(), user.password))
            throw UsernameNotFoundException("用户名密码错误")

        if (CollectionUtils.isEmpty(user.roles))
            throw UnAuthorizedException("本用户无权限访问")

        if (user.status == 4)
            throw LockedException("用户已被锁定")

        if (user.status == 0)
            throw DisabledException("用户已失效")

        if (user.status == 2)
            throw AccountExpiredException("用户已过期")

        val roles = user.roles!!.map { e -> "ROLE_${e.key?.toUpperCase()}" }.toTypedArray()

        return CustomAuthenticationToken(authentication.name, authentication.credentials.toString(), AuthorityUtils.createAuthorityList(*roles), user.zhName)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}