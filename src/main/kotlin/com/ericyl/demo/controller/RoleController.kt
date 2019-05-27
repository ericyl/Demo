package com.ericyl.demo.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class RoleController {


    @GetMapping("role.html")
    @PreAuthorize("hasPermission(#request, 'MinePermission')")
    fun role(model: Model, request: HttpServletRequest): String {
        return "role"
    }

}
