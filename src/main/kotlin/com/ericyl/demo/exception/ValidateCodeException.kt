package com.ericyl.demo.exception


import org.springframework.security.core.AuthenticationException

class ValidateCodeException(msg: String) : AuthenticationException(msg)
