package com.practice.concert_reservation_app.domain.user.repository

import com.practice.concert_reservation_app.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean
}