package com.practice.concert_reservation_app.global.error

import com.practice.concert_reservation_app.domain.concert.exception.ConcertNotFoundException
import com.practice.concert_reservation_app.domain.reservation.exception.InvalidSeatNumberException
import com.practice.concert_reservation_app.domain.reservation.exception.ReservationNotFoundException
import com.practice.concert_reservation_app.domain.reservation.exception.SeatAlreadyTakenException
import com.practice.concert_reservation_app.domain.user.exception.DuplicateUsernameException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException::class)
    fun handle(error: AuthenticationException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.AUTHENTICATION_FAILED
        val errorResponse = ErrorResponse.of(errorCode)
        
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(DuplicateUsernameException::class)
    fun handle(error: DuplicateUsernameException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.DUPLICATE_USERNAME
        val details = listOf(ErrorResponse.ErrorDetail("username: ${error.username}"))
        val errorResponse = ErrorResponse.of(errorCode, details)

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConcertNotFoundException::class)
    fun handle(error: ConcertNotFoundException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.CONCERT_NOT_FOUND
        val details = listOf(ErrorResponse.ErrorDetail("concertId: ${error.concertId}"))
        val errorResponse = ErrorResponse.of(errorCode, details)

        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidSeatNumberException::class)
    fun handle(error: InvalidSeatNumberException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.INVALID_SEAT_NUMBER
        val details = listOf(
                ErrorResponse.ErrorDetail("concertId: ${error.concertId}"),
                ErrorResponse.ErrorDetail("seatNumber: ${error.seatNumber}")
        )
        val errorResponse = ErrorResponse.of(errorCode, details)

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(SeatAlreadyTakenException::class)
    fun handle(error: SeatAlreadyTakenException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.SEAT_ALREADY_TAKEN
        val details = listOf(
                ErrorResponse.ErrorDetail("concertId: ${error.concertId}"),
                ErrorResponse.ErrorDetail("seatNumber: ${error.seatNumber}")
        )
        val errorResponse = ErrorResponse.of(errorCode, details)

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ReservationNotFoundException::class)
    fun handle(error: ReservationNotFoundException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.RESERVATION_NOT_FOUND
        val details = listOf(
                ErrorResponse.ErrorDetail("concertId: ${error.concertId}"),
                ErrorResponse.ErrorDetail("seatNumber: ${error.seatNumber}"),
                ErrorResponse.ErrorDetail("username: ${error.username}")
        )
        val errorResponse = ErrorResponse.of(errorCode, details)

        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }


}