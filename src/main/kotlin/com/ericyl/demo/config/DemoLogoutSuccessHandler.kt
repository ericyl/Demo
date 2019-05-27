package com.ericyl.demo.config

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class DemoLogoutSuccessHandler : LogoutSuccessHandler {

    @Throws(IOException::class)
    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        SecurityContextLogoutHandler().logout(request, response, authentication)
//        persistentTokenRepository.removeUserTokens(principal.getName())
        try {
            response.sendRedirect(request.getHeader("referer"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
