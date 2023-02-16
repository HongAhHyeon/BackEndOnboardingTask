//package com.onboarding.task.service.impl
//
//import com.onboarding.task.entity.Post
//import com.onboarding.task.dto.request.PostCreateDto
//import com.onboarding.task.dto.request.PostCreateRequest
//import com.onboarding.task.dto.request.PostUpdateRequest
//import com.onboarding.task.dto.response.PostDto
//import com.onboarding.task.entity.condition.PostSearchCondition
//import com.onboarding.task.repository.PostRepository
//import com.onboarding.task.repository.UserRepository
//import com.onboarding.task.service.PostService
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//import java.awt.print.Pageable
//@Transactional
//@Service
//class PostServiceImpl(
//    private val postRepository: PostRepository,
//    private val userRepository: UserRepository
//) : PostService {
//    override fun createPost(req: PostCreateRequest) {
//        val post = req.toEntity()
//
//        post.confirmWriter(userRepository.findByUserEmail()) ?: throw IllegalArgumentException("사용자 정보 없음.")
//        postRepository.save(post)
//    }
//
//    override fun updatePost(dto: PostUpdateRequest): PostDto {
//        TODO("Not yet implemented")
//    }
//
//    override fun deletePost(id: Long) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getPost(id: Long) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getPosts(pageable: Pageable, postSearchCondition: PostSearchCondition): List<PostDto> {
//        TODO("Not yet implemented")
//    }
//
//    private fun checkAuthority(post: Post, postExceptionType: PostExceptionType) {
//        if(!post.writer!!.userName.equals("로그인 사용자 이름"))
//            throw new PostException(postExceptionType)
//    }
//}