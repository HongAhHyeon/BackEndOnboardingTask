package com.onboarding.task.dto.response

import com.onboarding.task.entity.Member

data class MemberInfoResponse(
    val userEmail: String,
    val userName: String
) {

    companion object {
        fun of(member: Member): MemberInfoResponse {
            return MemberInfoResponse(
                userEmail = member.memberEmail,
                userName = member.memberName
            )
        }
    }
}