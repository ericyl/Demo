package com.ericyl.demo.config

import com.ericyl.demo.service.OperateService
import com.ericyl.demo.service.RoleService
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityConfig
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.util.AntPathMatcher
import org.springframework.util.CollectionUtils

class RbacFilterInvocationSecurityMetadataSource(private val operateService: OperateService?, private val roleService: RoleService?) : FilterInvocationSecurityMetadataSource {

    private val antPathMatcher = AntPathMatcher()


    @Throws(IllegalArgumentException::class)
    override fun getAttributes(any: Any): Collection<ConfigAttribute>? {
        val fi = any as FilterInvocation
        val url = fi.requestUrl
        val method = fi.request.method

        val operates = operateService?.list()

        val admin = roleService?.admin()

        if (!CollectionUtils.isEmpty(operates)) {
            val roles = operates?.firstOrNull { e ->
                e.method == method && antPathMatcher.match(e.url ?: "", url)
            }?.roles?.map { "ROLE_${it.key?.toUpperCase()}" }?.toTypedArray()
                    ?: arrayOf("ROLE_${admin ?: "ADMIN".toUpperCase()}")
            return SecurityConfig.createList(*roles)
        }

        if (admin != null)
            return SecurityConfig.createList("ROLE_${admin.toUpperCase()}")
        return null
    }

    override fun getAllConfigAttributes(): Collection<ConfigAttribute>? {
        return null
    }

    override fun supports(clazz: Class<*>): Boolean {
        return FilterInvocation::class.java.isAssignableFrom(clazz)
    }
}