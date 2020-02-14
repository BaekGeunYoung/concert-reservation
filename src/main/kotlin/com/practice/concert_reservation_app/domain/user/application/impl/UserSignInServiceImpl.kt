package com.practice.concert_reservation_app.domain.user.application.impl

import com.practice.concert_reservation_app.domain.user.application.UserSignInService
import com.practice.concert_reservation_app.domain.user.dto.SignInDto
import com.practice.concert_reservation_app.domain.user.repository.UserRepository
import com.practice.concert_reservation_app.global.config.security.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserSignInServiceImpl(
        @Autowired private val userRepository: UserRepository,
        @Autowired private val authenticationManager: AuthenticationManager,
        @Autowired private val jwtTokenProvider: JwtTokenProvider
): UserSignInService {
    override fun signIn(signInReq: SignInDto.SignInReq): SignInDto.SignInResult {
        val user = userRepository.findByUsername(signInReq.username)
        user?: throw UsernameNotFoundException("cannot find such user : ${signInReq.username}")

        val authenticator = UsernamePasswordAuthenticationToken(signInReq.username, signInReq.password)
        authenticationManager.authenticate(authenticator)

        val token = jwtTokenProvider.createToken(signInReq.username, user.roles.map { it.name })

        return SignInDto.SignInResult(username = signInReq.username, token = token)
    }
}