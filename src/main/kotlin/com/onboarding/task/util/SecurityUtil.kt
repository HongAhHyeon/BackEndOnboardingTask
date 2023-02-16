package com.onboarding.task.util

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class SecurityUtil {
    companion object {
        fun getSignInUsername(): String {
            val user = SecurityContextHolder.getContext().authentication.principal as UserDetails
            return user.username
        }
    }
}