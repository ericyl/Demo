package com.ericyl.demo.properties

import lombok.Data

@Data
class ValidateCodeProperties {
    var image: ImageCodeProperties? = null
    var sms: SmsCodeProperties? = null
}
