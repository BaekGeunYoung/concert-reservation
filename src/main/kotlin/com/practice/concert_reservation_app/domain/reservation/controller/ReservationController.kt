package com.practice.concert_reservation_app.domain.reservation.controller

import com.practice.concert_reservation_app.domain.reservation.application.ReservationService
import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/reservation")
class ReservationController(
        @Autowired val reservationService: ReservationService
) {
    @GetMapping("/concerts/{concertId}")
    fun getReservations(
            @PathVariable("concertId") concertId: Long
    ): ResponseEntity<Set<Reservation>> {
        val reservations = reservationService.getReservationsByConcert(concertId)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @PostMapping("/concerts/{concertId}/seats/{seatNumber}")
    fun reserve(
            @PathVariable("concertId") concertId: Long,
            @PathVariable("seatNumber") seatNumber: Int,
            @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Reservation> {
        val reservation = reservationService.reserve(concertId, seatNumber, userDetails.username)
        return ResponseEntity(reservation, HttpStatus.CREATED)
    }

    @DeleteMapping("/concerts/{concertId}/seats/{seatNumber}")
    fun cancelReservation(
            @PathVariable("concertId") concertId: Long,
            @PathVariable("seatNumber") seatNumber: Int,
            @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Unit> {
        reservationService.cancelReservation(concertId, seatNumber, userDetails.username)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PutMapping("/concerts/{concertId}/seats/{seatNumber}")
    fun modifyReservation(
            @PathVariable("concertId") concertId: Long,
            @PathVariable("seatNumber") seatNumber: Int,
            @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Reservation> {
        val modifiedReservation = reservationService.modifyReservation(concertId, seatNumber, userDetails.username)
        return ResponseEntity(modifiedReservation, HttpStatus.OK)
    }
}