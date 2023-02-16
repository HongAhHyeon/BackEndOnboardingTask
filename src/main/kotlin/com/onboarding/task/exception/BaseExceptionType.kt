package com.onboarding.task.exception

import org.springframework.http.HttpStatus

interface BaseExceptionType {
    fun getErrorCode() : Int
    fun getHttpStatus() : HttpStatus
    fun getErrorMessage() : String
}