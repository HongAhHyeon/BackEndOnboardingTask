package com.onboarding.task.dto.request

import com.onboarding.task.entity.Member
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.springframework.security.crypto.password.PasswordEncoder

data class MemberSignUpRequest(
    @NotNull
    @Email
    var userEmail: String,
    @NotNull
    var userPw: String,
    @NotNull
    var userName: String
) {

    fun toEntity(encoder : PasswordEncoder) : Member {
        return Member (
            memberEmail = userEmail,
            memberPw = encoder.encode(userPw),
            memberName = userName,
        )
    }
}