package com.practice.concert_reservation_app.domain.user.application.impl

import com.practice.concert_reservation_app.domain.user.application.UserFindService
import com.practice.concert_reservation_app.domain.user.domain.User
import com.practice.concert_reservation_app.domain.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserFindServiceImpl(
        @Autowired private val userRepository: UserRepository
): UserFindService {
    override fun findByUsername(username: String): User {
        val user = userRepository.findByUsername(username)
        user?: throw UsernameNotFoundException("cannot find such user : $username")

        return user
    }
}