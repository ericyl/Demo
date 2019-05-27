package com.ericyl.demo.model.http

import com.ericyl.demo.common.CODE_SUCCESS
import com.ericyl.demo.exception.RestException


data class RestResult(val code: Int = CODE_SUCCESS, val message: String? = null, var data: Any? = null) {
    //    constructor(data: T?) : this(CODE_SUCCESS, null, data)
//    constructor(message: String?) : this(CODE_AN_UNKNOWN_ERROR, message)
    constructor(e: RestException) : this(e.code, e.message)
}
