package com.practice.concert_reservation_app.domain.user.controller

import com.practice.concert_reservation_app.domain.reservation.application.GetReservationService
import com.practice.concert_reservation_app.domain.reservation.controller.ReservationController
import com.practice.concert_reservation_app.domain.user.application.UserMyInfoService
import com.practice.concert_reservation_app.domain.user.application.UserSignInService
import com.practice.concert_reservation_app.domain.user.application.UserSignUpService
import com.practice.concert_reservation_app.domain.user.domain.User
import com.practice.concert_reservation_app.domain.user.dto.MyPageDto
import com.practice.concert_reservation_app.domain.user.dto.SignInDto
import com.practice.concert_reservation_app.domain.user.dto.SignUpDto
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
@RequestMapping("api/v1/user")
class UserController(
        @Autowired private val userSignUpService: UserSignUpService,
        @Autowired private val userSignInService: UserSignInService,
        @Autowired private val userMyInfoService: UserMyInfoService,
        @Autowired private val getReservationService: GetReservationService
) {
    private val logger: Logger = LoggerFactory.getLogger(ReservationController::class.java)
    private val className: String = this::class.java.simpleName

    @PostMapping("/register")
    fun register(@RequestBody @Valid signUpReq: SignUpDto.SignUpReq): ResponseEntity<User> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        val user = userSignUpService.signUp(signUpReq)
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid signInReq: SignInDto.SignInReq): ResponseEntity<SignInDto.SignInResult> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        val signInResult = userSignInService.signIn(signInReq)
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(signInResult, HttpStatus.OK)
    }

    @GetMapping("/my_page")
    fun myPage(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<MyPageDto.MyPageResult> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        val userInfo = userMyInfoService.myInfo(userDetails.username)
        val reservationInfo = getReservationService.getReservationsByUser(userDetails.username)

        val myPageResult = MyPageDto.MyPageResult(userInfo, reservationInfo)
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(myPageResult, HttpStatus.OK)
    }
}