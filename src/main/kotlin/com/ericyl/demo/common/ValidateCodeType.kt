package com.ericyl.demo.common

enum class ValidateCodeType {
    SMS {
        override val paramNameOnValidate: String
            get() = DEFAULT_PARAMETER_NAME_CODE_SMS
    },
    IMAGE {
        override val paramNameOnValidate: String
            get() = DEFAULT_PARAMETER_NAME_CODE_IMAGE
    };

    abstract val paramNameOnValidate: String

}
