package com.onboarding.task.config.security.principal

interface Principal {
    fun getId(): Long
    fun getAuthorId(): Long
    fun getType(): PrincipalType
    fun getUsername(): String
}