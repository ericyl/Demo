package com.ericyl.demo.properties

import lombok.Data

@Data
open class SmsCodeProperties {
    var count: Int? = 4
    var expiredSeconds: Int? = 60
    var urls: Array<String>? = null
}