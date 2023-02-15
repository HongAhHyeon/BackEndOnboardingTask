//package com.onboarding.task.service.impl
//
//import com.onboarding.task.entity.Post
//import com.onboarding.task.dto.request.PostCreateDto
//import com.onboarding.task.dto.request.PostModifyDto
//import com.onboarding.task.dto.response.PostDto
//import com.onboarding.task.repository.PostRepository
//import com.onboarding.task.service.PostService
//import org.springframework.stereotype.Service
//
//@Service
//class PostServiceImpl(
//    private val postRepository: PostRepository
//) : PostService {
//    override fun createPost(dto: PostCreateDto) {
//        val post = Post(dto.user, dto.title, dto.content)
//        postRepository.save(post)
//    }
//
//    override fun updatePost(dto: PostModifyDto): PostDto {
//        return PostDto()
//    }
//
//    override fun deletePost(id: Long) {
//    }
//
//    override fun getPost(id: Long) {
//    }
//
//    override fun getPosts(): List<PostDto> {
//    }
//}