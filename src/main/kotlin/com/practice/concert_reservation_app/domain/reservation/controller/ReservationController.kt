package com.practice.concert_reservation_app.domain.reservation.controller

import com.practice.concert_reservation_app.domain.reservation.application.GetReservationService
import com.practice.concert_reservation_app.domain.reservation.application.HandleReservationService
import com.practice.concert_reservation_app.domain.reservation.application.ReservationService
import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import com.practice.concert_reservation_app.domain.reservation.dto.ReservationDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/reservation")
class ReservationController(
        @Autowired val reservationService: ReservationService,
        @Autowired val getReservationService: GetReservationService,
        @Autowired val handleReservationService: HandleReservationService
) {
    private val logger: Logger = LoggerFactory.getLogger(ReservationController::class.java)
    private val className: String = this::class.java.simpleName

    @GetMapping("/concerts/{concertId}")
    fun getReservations(
            @PathVariable("concertId") concertId: Long
    ): ResponseEntity<List<Reservation>> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        val reservations = getReservationService.getReservationsByConcert(concertId)
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @PostMapping("/concerts/{concertId}/seats/{seatNumber}")
    fun reserve(
            @PathVariable("concertId") concertId: Long,
            @PathVariable("seatNumber") seatNumber: Int,
            @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Reservation> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        val reservation = reservationService.reserve(concertId, seatNumber, userDetails.username)
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(reservation, HttpStatus.CREATED)
    }

    @DeleteMapping("/concerts/{concertId}/seats/{seatNumber}")
    fun cancelReservation(
            @PathVariable("concertId") concertId: Long,
            @PathVariable("seatNumber") seatNumber: Int,
            @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Unit> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        handleReservationService.cancelReservation(concertId, seatNumber, userDetails.username)
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PutMapping("/concerts/{concertId}/seats/{seatNumber}")
    fun modifyReservation(
            @PathVariable("concertId") concertId: Long,
            @PathVariable("seatNumber") seatNumber: Int,
            @RequestBody @Valid modifyReq: ReservationDto.ModifyReq,
            @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Reservation> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        val modifiedReservation = handleReservationService.modifyReservation(concertId, seatNumber, modifyReq.newSeatNumber, userDetails.username)
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(modifiedReservation, HttpStatus.OK)
    }
}