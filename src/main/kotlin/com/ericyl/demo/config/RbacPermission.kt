package com.ericyl.demo.config


import com.ericyl.demo.service.OperateService
import com.ericyl.demo.service.RoleService
import org.apache.commons.lang3.StringUtils
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.util.AntPathMatcher
import org.springframework.util.CollectionUtils
import java.io.Serializable
import javax.servlet.http.HttpServletRequest

class RbacPermission(private val operateService: OperateService?, private val roleService: RoleService?) : PermissionEvaluator {

    private val antPathMatcher = AntPathMatcher()

    override fun hasPermission(authentication: Authentication, targetDomainObject: Any, permission: Any): Boolean {
        val request = targetDomainObject as HttpServletRequest
        val requestURI = request.requestURI
        val method = request.method
        val authorities = authentication.authorities

        if (CollectionUtils.isEmpty(authorities))
            return false

        val adminRole = roleService?.admin()
        if (StringUtils.isNotBlank(adminRole) && authorities.map(GrantedAuthority::getAuthority).filter { it.toUpperCase().substring(5) == adminRole }.any())
            return true

        return operateService?.rbac(authorities.map(GrantedAuthority::getAuthority).toTypedArray())?.filter { e ->
            e.method == method && antPathMatcher.match(e.url ?: "", requestURI)
        }?.any()
                ?: false

    }

    override fun hasPermission(authentication: Authentication, targetId: Serializable, targetType: String, permission: Any): Boolean {
        return false
    }

}
