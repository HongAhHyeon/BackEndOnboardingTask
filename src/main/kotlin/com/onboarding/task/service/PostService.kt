package com.onboarding.task.service

import com.onboarding.task.dto.request.PostCreateDto
import com.onboarding.task.dto.request.PostModifyDto
import com.onboarding.task.dto.request.UserCreateDto
import com.onboarding.task.dto.response.PostDto
import com.onboarding.task.dto.response.UserDto

interface PostService {

    fun createPost(dto: PostCreateDto)

    fun updatePost(dto : PostModifyDto) : PostDto

    fun deletePost(id: Long)

    fun getPost(id: Long)

    fun getPosts(): List<PostDto>
}