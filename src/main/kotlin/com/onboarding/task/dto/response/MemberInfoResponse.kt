package com.onboarding.task.dto.response

import com.onboarding.task.entity.Member

data class MemberInfoResponse(
    val memberId: Long,
    val memberEmail: String,
    val memberName: String
) {

    companion object {
        fun of(member: Member): MemberInfoResponse {
            return MemberInfoResponse(
                memberId = member.id!!,
                memberEmail = member.memberEmail,
                memberName = member.memberName
            )
        }
    }
}