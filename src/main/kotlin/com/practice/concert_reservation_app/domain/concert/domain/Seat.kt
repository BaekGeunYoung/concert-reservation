package com.practice.concert_reservation_app.domain.concert.domain

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity @IdClass(SeatId::class)
data class Seat (
        @Id
        var concertId: Long? = null,
        @Id
        var seatNumber: Int? = null,
        var isTaken: Boolean
) {
    constructor(): this(isTaken = false)
}

class SeatId(
        var concertId: Long? = null,
        var seatNumber: Int? = null
): Serializable