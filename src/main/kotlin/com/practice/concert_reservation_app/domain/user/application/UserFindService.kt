package com.practice.concert_reservation_app.domain.user.application

import com.practice.concert_reservation_app.domain.user.domain.User

interface UserFindService {
    fun findByUsername(username: String): User
}