package com.practice.concert_reservation_app.domain.concert.domain

import javax.persistence.Embeddable

@Embeddable
data class Seat (
        var seatNumber: Int? = null,
        var isTaken: Boolean
) {
    constructor(): this(isTaken = false)
}