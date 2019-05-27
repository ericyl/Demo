package com.ericyl.demo.config

import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityConfig
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.Authentication

class RbacAccessDecisionManager : AccessDecisionManager {

    @Throws(AccessDeniedException::class, InsufficientAuthenticationException::class)
    override fun decide(authentication: Authentication, any: Any,
                        configAttributes: Collection<ConfigAttribute>?) {
        if (configAttributes == null)
            return

        configAttributes.forEach {
            val needRole = (it as SecurityConfig).attribute
            for (ga in authentication.authorities) {
                if (needRole.trim { e -> e <= ' ' } == ga.authority.trim { e -> e <= ' ' }) {
                    return
                }
            }
        }

        throw AccessDeniedException("无权限！")
    }

    override fun supports(attribute: ConfigAttribute): Boolean {
        return true
    }

    override fun supports(clazz: Class<*>): Boolean {
        return true
    }

}