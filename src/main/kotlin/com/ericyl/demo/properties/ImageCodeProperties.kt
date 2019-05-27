package com.ericyl.demo.properties

import lombok.Data

@Data
class ImageCodeProperties : SmsCodeProperties() {
    var width: Int? = 150
    var height: Int? = 60
}
