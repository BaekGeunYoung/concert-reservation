package com.practice.concert_reservation_app.domain.user.application

import com.practice.concert_reservation_app.domain.user.domain.User
import com.practice.concert_reservation_app.domain.user.dto.SignInDto
import com.practice.concert_reservation_app.domain.user.dto.SignUpDto
import org.springframework.http.ResponseEntity

interface UserService {
    fun signUp(signUpReq: SignUpDto.SignUpReq): User
    fun signIn(signInReq: SignInDto.SignInReq): SignInDto.SignInResult
    fun myInfo(username: String): User
}