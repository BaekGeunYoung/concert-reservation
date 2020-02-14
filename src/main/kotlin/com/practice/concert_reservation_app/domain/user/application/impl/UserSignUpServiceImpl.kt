package com.practice.concert_reservation_app.domain.user.application.impl

import com.practice.concert_reservation_app.domain.user.application.UserSignUpService
import com.practice.concert_reservation_app.domain.user.domain.User
import com.practice.concert_reservation_app.domain.user.dto.SignUpDto
import com.practice.concert_reservation_app.domain.user.exception.DuplicateUsernameException
import com.practice.concert_reservation_app.domain.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserSignUpServiceImpl(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val bCryptPasswordEncoder: BCryptPasswordEncoder
): UserSignUpService {
    override fun signUp(signUpReq: SignUpDto.SignUpReq): User {
        if(userRepository.existsByUsername(signUpReq.username)) {
            throw DuplicateUsernameException(signUpReq.username)
        }

        val newUser = signUpReq.toEntity(bCryptPasswordEncoder)
        return userRepository.save(newUser)
    }
}