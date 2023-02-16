package com.onboarding.task.repository

import com.onboarding.task.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun existsByUserEmail(userEmail: String): Boolean
    fun existsByUserName(userName: String): Boolean
    fun findByUserEmail(userEmail: String): User?

}