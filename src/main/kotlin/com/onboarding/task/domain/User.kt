package com.onboarding.task.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class User (
    @Id
    val userId: String,
    val userPw: String
)