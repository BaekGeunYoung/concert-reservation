package com.practice.concert_reservation_app.global.error

import com.practice.concert_reservation_app.domain.user.exception.DuplicateUsernameException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException::class)
    fun handle(error: AuthenticationException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.AUTHENTICATION_FAILED
        val errorResponse = ErrorResponse.of(errorCode)
        
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateUsernameException::class)
    fun handle(error: DuplicateUsernameException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.DUPLICATE_USERNAME
        val details = listOf(ErrorResponse.ErrorDetail("username : ${error.username}"))
        val errorResponse = ErrorResponse.of(errorCode, details)

        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }
}