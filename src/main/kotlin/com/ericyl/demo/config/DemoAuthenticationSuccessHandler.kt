package com.ericyl.demo.config

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DemoAuthenticationSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {

    private val requestCache = HttpSessionRequestCache()

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse,
                                         authentication: Authentication) {

        var savedRequest: SavedRequest? = requestCache.getRequest(request, response)
        if (savedRequest != null && savedRequest.redirectUrl.endsWith("/error"))
            savedRequest = null
        if (savedRequest == null) {
            requestCache.removeRequest(request, response)
            defaultTargetUrl = "/"
            isAlwaysUseDefaultTargetUrl = true
        }
        super.onAuthenticationSuccess(request, response, authentication)

    }

}
