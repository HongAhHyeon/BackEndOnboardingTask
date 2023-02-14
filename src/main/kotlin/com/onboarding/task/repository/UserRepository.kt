package com.onboarding.task.repository

import com.onboarding.task.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserEmail(userEmail: String): User?

}