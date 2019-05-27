package com.ericyl.demo.config

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DemoAuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {


    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        setDefaultFailureUrl("/signIn.html")
        super.onAuthenticationFailure(request, response, exception)

    }
}
