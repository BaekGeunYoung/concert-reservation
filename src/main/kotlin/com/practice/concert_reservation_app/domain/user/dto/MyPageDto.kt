package com.practice.concert_reservation_app.domain.user.dto

import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import com.practice.concert_reservation_app.domain.user.domain.User

class MyPageDto {
    data class MyPageResult(
            val userInfo: User,
            val reservationInfo: Set<Reservation>
    )
}