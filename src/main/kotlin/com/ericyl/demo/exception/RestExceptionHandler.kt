package com.ericyl.demo.exception

import com.ericyl.demo.common.CODE_AN_UNKNOWN_ERROR
import com.ericyl.demo.common.CODE_BAD_REQUEST
import com.ericyl.demo.model.http.RestResult
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


//@Slf4j
@ControllerAdvice(basePackages = ["com.dots.invoice.hospital.management.controller.rest", "com.dots.invoice.hospital.management.controller.api"])
class RestExceptionHandler {


    @ExceptionHandler(Exception::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handle(e: Exception): RestResult {
        e.printStackTrace()
        return when (e) {
            is RestException -> RestResult(e)
            is org.springframework.web.servlet.NoHandlerFoundException -> RestResult(CODE_BAD_REQUEST, e.message)
            else -> RestResult(CODE_AN_UNKNOWN_ERROR, e.message)
        }
    }

}
