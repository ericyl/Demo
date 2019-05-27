package com.ericyl.demo.config

import com.ericyl.demo.service.OperateService
import com.ericyl.demo.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class RbacMethodSecurityConfig : GlobalMethodSecurityConfiguration() {

    @Autowired
    private val operateService: OperateService? = null

    @Autowired
    private val roleService: RoleService? = null


    override fun createExpressionHandler(): MethodSecurityExpressionHandler {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()

        expressionHandler.setPermissionEvaluator(RbacPermission(operateService, roleService))
        return expressionHandler
    }
}
