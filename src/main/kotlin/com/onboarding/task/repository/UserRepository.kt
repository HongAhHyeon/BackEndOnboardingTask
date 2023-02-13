package com.onboarding.task.repository

import com.onboarding.task.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>