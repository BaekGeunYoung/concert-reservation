package com.practice.concert_reservation_app.global.error

data class ErrorResponse(
        var message: String,
        var code: String,
        var details: List<ErrorDetail> = listOf()
) {
    class ErrorDetail(
            val message: String
    )

    companion object {
        fun of(errorCode: ErrorCode, details: List<ErrorDetail>? = null): ErrorResponse {
            val errorResponse = ErrorResponse(
                    message = errorCode.message,
                    code = errorCode.code
            )

            details?.let { errorResponse.details = it }

            return errorResponse
        }
    }
}