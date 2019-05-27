package com.ericyl.demo.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class OperateController {


    @GetMapping("operate.html")
    @PreAuthorize("hasPermission(#request, 'MinePermission')")
    fun operate(model: Model, request: HttpServletRequest): String {
        return "operate"
    }

}
