package com.ericyl.demo.config

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class CustomAuthenticationToken(principal: Any, credentials: Any, authorities: Collection<GrantedAuthority>, val zhName: String?) : UsernamePasswordAuthenticationToken(principal, credentials, authorities)