package com.faiz.baseproject.utils

enum class ResponseCode(
    var value: Int
) {
    // ===== POSITIVE ===== //
    SUCCESS(200),
    CREATED(201),
    NOCONTENT(204),
    SUCEESS_ERROR_MIN(205),
    SUCEESS_ERROR_MAX(299),


    // ===== NEGATIVE ===== //
    UNKNOWN(4232),
    ERROR(400),
    CONNECTION_TIMEOUT(408),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    FORGED_RESPONSE(-899),

    // ===== RECEIVED FROM SERVER ===== //
    GENERAL_ERROR(-1),
    SESSION_TIMEOUT(-2),
    INTERNAL_SERVER_ERROR(500);

    companion object {
        fun getCode(errorCode: Int): ResponseCode {
            for (code in values()) {
                if (code.value == errorCode) return code
            }
            throw IllegalArgumentException("Provided error code doesn't exit")
        }
    }
}