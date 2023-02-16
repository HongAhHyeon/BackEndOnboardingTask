package com.onboarding.task.dto.response

import com.onboarding.task.entity.User

data class UserInfoResponse(
    val userEmail: String,
    val userName: String
) {

    companion object {
        fun of(user: User): UserInfoResponse {
            return UserInfoResponse(
                userEmail = user.userEmail,
                userName = user.userName
            )
        }
    }
}