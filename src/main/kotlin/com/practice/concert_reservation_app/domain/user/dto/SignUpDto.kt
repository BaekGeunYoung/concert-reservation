package com.practice.concert_reservation_app.domain.user.dto

import com.practice.concert_reservation_app.domain.user.domain.Role
import com.practice.concert_reservation_app.domain.user.domain.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SignUpDto {
    data class SignUpReq(
            val username: String,
            val password: String,
            val firstName: String,
            val lastName: String
    ) {
        fun toEntity(bCryptPasswordEncoder: BCryptPasswordEncoder)
        = User(
                username = username,
                password = bCryptPasswordEncoder.encode(password),
                firstName = firstName,
                lastName = lastName,
                roles = mutableListOf(Role.USER)
        )
    }
}