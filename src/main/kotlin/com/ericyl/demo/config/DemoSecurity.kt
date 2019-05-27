package com.ericyl.demo.config

import com.ericyl.demo.service.OperateService
import com.ericyl.demo.service.RoleService
import com.ericyl.demo.filter.ValidateCodeFilter
import com.ericyl.demo.properties.DemoProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.access.intercept.AbstractSecurityInterceptor
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

@Configuration
@EnableWebSecurity
class DemoSecurity : WebSecurityConfigurerAdapter() {


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    private val demoAuthenticationFailureHandler: AuthenticationFailureHandler? = null

    @Autowired
    private val demoLogoutSuccessHandler: LogoutSuccessHandler? = null

    @Autowired
    private val demoAuthenticationSuccessHandler: AuthenticationSuccessHandler? = null

    @Autowired
    private val operateService: OperateService? = null

    @Autowired
    private val roleService: RoleService? = null

    @Autowired
    private val customAuthenticationProvider: AuthenticationProvider? = null

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null

    @Autowired
    private val properties: DemoProperties? = null


    override fun configure(auth: AuthenticationManagerBuilder?) {
//        super.configure(auth)
        auth?.authenticationProvider(customAuthenticationProvider!!)//?.userDetailsService(hospitalManagementUserDetailService)?.passwordEncoder(passwordEncoder())
    }


    override fun configure(http: HttpSecurity) {
        val validateCodeFilter = ValidateCodeFilter(demoAuthenticationFailureHandler, redisTemplate, properties)
        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter::class.java)
                .headers().frameOptions().sameOrigin()
                .and().formLogin().loginPage("/signIn.html").loginProcessingUrl("/authentication/login").successHandler(demoAuthenticationSuccessHandler).failureHandler(demoAuthenticationFailureHandler)
                .and().logout().clearAuthentication(true).deleteCookies("JSESSIONID", "remember-me").logoutSuccessHandler(demoLogoutSuccessHandler)
                .and().authorizeRequests()
                .antMatchers("/signIn.html", "/authentication/login", "/code/**", "/api/invoice", "/api/invoice/**", "/hospital/invoice/**").permitAll()
//                .antMatchers("/logout", "/login**", "/api/invoice", "/api/invoice/**", "/hospital/invoice/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
    }

    override fun configure(web: WebSecurity) {
        super.configure(web)
        web.privilegeEvaluator(DefaultWebInvocationPrivilegeEvaluator(customerFilterSecurityInterceptor()))
                .ignoring().antMatchers("/**/*.js", "/**/*.css", "/**/*.jpg", "/**/**.map")
    }

    private fun customerFilterSecurityInterceptor(): AbstractSecurityInterceptor {
        val interceptor = FilterSecurityInterceptor()
        interceptor.accessDecisionManager = RbacAccessDecisionManager()
        interceptor.securityMetadataSource = RbacFilterInvocationSecurityMetadataSource(operateService, roleService)
        return interceptor
    }
}