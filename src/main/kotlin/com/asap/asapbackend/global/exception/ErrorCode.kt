package com.asap.asapbackend.global.exception

import org.springframework.http.HttpStatusCode

interface ErrorCode{
    val code: String
    val message: String
    val httpStatusCode: HttpStatusCode

    companion object {
        const val INTERNAL_SERVER_ERROR = "0000"
    }
}