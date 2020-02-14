package com.practice.concert_reservation_app.domain.user.application

import com.practice.concert_reservation_app.domain.user.dto.SignInDto

interface UserSignInService {
    fun signIn(signInReq: SignInDto.SignInReq): SignInDto.SignInResult
}