package com.practice.concert_reservation_app.domain.reservation.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity @IdClass(Reservation::class)
data class Reservation(
        @Id
        @Column(name="username", length = 200)
        var username: String,
        @Id
        var concertId: Long,
        @Id
        var seatNumber: Int
): Serializable {
    constructor() : this(username = "", concertId = 0, seatNumber = 0)
}