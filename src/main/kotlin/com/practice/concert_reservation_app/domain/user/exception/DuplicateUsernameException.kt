package com.practice.concert_reservation_app.domain.user.exception

import java.lang.RuntimeException

class DuplicateUsernameException(val username: String): RuntimeException()