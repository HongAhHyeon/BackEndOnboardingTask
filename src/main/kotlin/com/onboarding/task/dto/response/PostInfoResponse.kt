package com.onboarding.task.dto.response

import com.onboarding.task.entity.Post


class PostInfoResponse (
    val title: String,
    val content: String,
) {

    companion object {
        fun of(post: Post): PostInfoResponse {
            return PostInfoResponse(
                title = post.title,
                content = post.content
            )
        }
    }
}