package com.practice.concert_reservation_app.global.exception

import java.lang.RuntimeException

class JwtValidationException(override val message: String?) : RuntimeException()