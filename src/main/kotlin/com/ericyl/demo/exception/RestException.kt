package com.ericyl.demo.exception

import com.ericyl.demo.model.http.RestResult

class RestException : RuntimeException {

    val code: Int

    constructor(code: Int, cause: Throwable) : super(cause) {
        this.code = code
    }

    constructor(code: Int, message: String, cause: Throwable) : super(message, cause) {
        this.code = code
    }

    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    constructor(result: RestResult) : super(result.message) {
        this.code = result.code
    }

}