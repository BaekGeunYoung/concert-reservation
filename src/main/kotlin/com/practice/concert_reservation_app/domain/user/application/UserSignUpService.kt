package com.practice.concert_reservation_app.domain.user.application

import com.practice.concert_reservation_app.domain.user.domain.User
import com.practice.concert_reservation_app.domain.user.dto.SignUpDto

interface UserSignUpService {
    fun signUp(signUpReq: SignUpDto.SignUpReq): User
}