package com.ericyl.demo.exception

import org.springframework.security.core.AuthenticationException


class UnAuthorizedException(val msg: String) : AuthenticationException(msg)