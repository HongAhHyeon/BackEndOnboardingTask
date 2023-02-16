package com.onboarding.task.service

import com.onboarding.task.dto.request.PostCreateRequest
import com.onboarding.task.dto.request.PostUpdateRequest
import com.onboarding.task.dto.response.PostDto
import com.onboarding.task.entity.condition.PostSearchCondition
import java.awt.print.Pageable

interface PostService {

    fun createPost(req: PostCreateRequest)

    fun updatePost(dto : PostUpdateRequest) : PostDto

    fun deletePost(id: Long)

    fun getPost(id: Long)

    fun getPosts(pageable: Pageable,postSearchCondition: PostSearchCondition): List<PostDto>
}