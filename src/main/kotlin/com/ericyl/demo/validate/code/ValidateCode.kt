package com.ericyl.demo.validate.code

import lombok.Data

import java.io.Serializable

@Data
open class ValidateCode : Serializable {
    var code: String? = null
    var expiredSeconds: Int? = null

    constructor(code: String?, expiredSeconds: Int?) {
        this.code = code
        this.expiredSeconds = expiredSeconds
    }
}
