package com.practice.concert_reservation_app.global.config.bean

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class BeanConfig {
    @Bean
    fun bCryptPasswordEncoder() : BCryptPasswordEncoder = BCryptPasswordEncoder()
}