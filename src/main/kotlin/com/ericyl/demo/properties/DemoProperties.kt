package com.ericyl.demo.properties

import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Data
@Component
@ConfigurationProperties(prefix = "properties")
class DemoProperties {
    var validateCode: ValidateCodeProperties? = null
}
