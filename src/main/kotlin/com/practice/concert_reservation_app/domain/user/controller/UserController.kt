package com.practice.concert_reservation_app.domain.user.controller

import com.practice.concert_reservation_app.domain.reservation.application.ReservationService
import com.practice.concert_reservation_app.domain.user.application.UserService
import com.practice.concert_reservation_app.domain.user.domain.User
import com.practice.concert_reservation_app.domain.user.dto.MyPageDto
import com.practice.concert_reservation_app.domain.user.dto.SignInDto
import com.practice.concert_reservation_app.domain.user.dto.SignUpDto
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
    @Autowired private val userService: UserService,
    @Autowired private val reservationService: ReservationService
) {
    @PostMapping("/register")
    fun register(@RequestBody @Valid signUpReq: SignUpDto.SignUpReq): ResponseEntity<User> {
        val user = userService.signUp(signUpReq)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid signInReq: SignInDto.SignInReq): ResponseEntity<SignInDto.SignInResult> {
        val signInResult = userService.signIn(signInReq)
        return ResponseEntity(signInResult, HttpStatus.OK)
    }

    @GetMapping("/my_page")
    fun myPage(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<MyPageDto.MyPageResult> {
        val userInfo = userService.myInfo(userDetails.username)
        val reservationInfo = reservationService.getReservationsByUser(userDetails.username)

        val myPageResult = MyPageDto.MyPageResult(userInfo, reservationInfo)
        return ResponseEntity(myPageResult, HttpStatus.OK)
    }
}