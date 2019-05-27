package com.ericyl.demo.validate.code

import org.springframework.web.context.request.ServletWebRequest

interface ValidateCodeGenerator<T : ValidateCode> {

    fun generator(request: ServletWebRequest): T
}
