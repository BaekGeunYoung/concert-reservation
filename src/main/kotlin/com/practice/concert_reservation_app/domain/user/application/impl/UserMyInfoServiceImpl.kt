package com.practice.concert_reservation_app.domain.user.application.impl

import com.practice.concert_reservation_app.domain.user.application.UserMyInfoService
import com.practice.concert_reservation_app.domain.user.domain.User
import com.practice.concert_reservation_app.domain.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserMyInfoServiceImpl(
        @Autowired private val userRepository: UserRepository
): UserMyInfoService {
    override fun myInfo(username: String): User {
        return userRepository.findByUsername(username)!!
    }
}