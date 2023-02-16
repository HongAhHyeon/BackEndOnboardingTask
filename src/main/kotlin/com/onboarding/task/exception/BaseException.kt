package com.onboarding.task.exception

abstract class BaseException : RuntimeException() {
    abstract fun getExceptionType() : BaseExceptionType
}