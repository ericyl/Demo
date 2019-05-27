package com.ericyl.demo.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class UserController {


    @GetMapping("user.html")
    @PreAuthorize("hasPermission(#request, 'MinePermission')")
    fun main(model: Model, request: HttpServletRequest): String {
        return "user"
    }

}
