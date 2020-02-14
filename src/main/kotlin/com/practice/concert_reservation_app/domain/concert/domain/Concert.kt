package com.practice.concert_reservation_app.domain.concert.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Concert(
        @Id @GeneratedValue
        var id: Long? = null,

        var concertName: String? = null,

        var startTime: LocalDateTime? = null,

        var endTime: LocalDateTime? = null,

        @ElementCollection(fetch = FetchType.EAGER)
        var seats: MutableSet<Seat>? = null
) {
    constructor() : this(concertName = "")

    fun isValidSeat(seatNumber: Int): Boolean {
        val targetSeat = seats!!.find { it.seatNumber == seatNumber }

        targetSeat?: return false
        return true
    }

    fun canReserve(seatNumber: Int): Boolean {
        val targetSeat = seats!!.find { it.seatNumber == seatNumber }

        return targetSeat!!.isTaken
    }

    fun takeSeat(seatNumber: Int) {
        val targetSeat = seats!!.find { it.seatNumber == seatNumber }
        targetSeat!!.isTaken = true
    }

    fun untakeSeat(seatNumber: Int) {
        val targetSeat = seats!!.find { it.seatNumber == seatNumber }
        targetSeat!!.isTaken = false
    }
}