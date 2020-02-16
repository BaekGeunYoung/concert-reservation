package com.practice.concert_reservation_app.domain.reservation.dto

class ReservationDto {
    data class ModifyReq(
            var newSeatNumber: Int
    )
}