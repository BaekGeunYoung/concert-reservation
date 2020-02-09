package com.practice.concert_reservation_app.global.config.security.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfig(
        @Autowired private val jwtTokenProvider: JwtTokenProvider
): SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity?) {
        val customFilter = JwtTokenFilter(jwtTokenProvider)
        builder?.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}