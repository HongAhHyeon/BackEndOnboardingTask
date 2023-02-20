package com.onboarding.task.config.security.principal

import com.onboarding.task.config.security.principal.Principal
import com.onboarding.task.config.security.principal.PrincipalType

class UserPrincipal(
    private val userId: Long,
    private val authorId: Long,
    private val userName: String
) : Principal {

    override fun getId(): Long {
        return userId
    }

    override fun getAuthorId(): Long {
        return authorId
    }

    override fun getType(): PrincipalType {
        return PrincipalType.USER
    }

    override fun getUsername(): String {
        return userName
    }
}