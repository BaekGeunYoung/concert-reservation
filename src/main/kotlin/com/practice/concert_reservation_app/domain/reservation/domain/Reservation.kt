package com.practice.concert_reservation_app.domain.reservation.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Reservation(
        @Id
        var userId: Long,
        @Id
        var concertId: Long,
        @Id
        var seatNumber: Int
) {
    constructor() : this(userId = 0, concertId = 0, seatNumber = 0)
}