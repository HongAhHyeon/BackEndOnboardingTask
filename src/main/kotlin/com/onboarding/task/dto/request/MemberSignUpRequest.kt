package com.onboarding.task.dto.request

import com.onboarding.task.entity.Member
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.security.crypto.password.PasswordEncoder

data class MemberSignUpRequest(
    @NotEmpty(message = "회원 이메일은 필수입니다.")
    @Email
    var memberEmail: String,
    @NotEmpty(message = "회원 비밀번호는 필수입니다.")
    var memberPw: String,
    @NotEmpty(message = "회원 이름은 필수입니다.")
    var memberName: String
) {

    fun toEntity(encoder : PasswordEncoder) : Member {
        return Member (
            memberEmail = memberEmail,
            memberPw = encoder.encode(memberPw),
            memberName = memberName,
        )
    }

    constructor() : this("", "", "")
}