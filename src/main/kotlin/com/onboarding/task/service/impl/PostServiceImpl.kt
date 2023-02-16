package com.onboarding.task.service.impl

import com.onboarding.task.entity.Post
import com.onboarding.task.dto.request.PostCreateRequest
import com.onboarding.task.dto.request.PostUpdateRequest
import com.onboarding.task.dto.response.PostDto
import com.onboarding.task.dto.response.PostInfoResponse
import com.onboarding.task.entity.condition.PostSearchCondition
import com.onboarding.task.repository.PostRepository
import com.onboarding.task.repository.UserRepository
import com.onboarding.task.service.PostService
import com.onboarding.task.util.SecurityUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.awt.print.Pageable
@Transactional
@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : PostService {
    override fun createPost(req: PostCreateRequest) {
        val post = req.toEntity()

        post.confirmWriter(userRepository.findByUserEmail(SecurityUtil.getSignInUsername()) ?: throw IllegalArgumentException("사용자 정보 없음."))
        postRepository.save(post)
    }

    override fun updatePost(req: PostUpdateRequest) {
        val post = postRepository.findByIdOrNull(req.id) ?: throw Exception("게시글 정보 없음.")
        checkAuthority(post)
        post.updateTitle(req.title)
        post.updateContent(req.content)
    }

    override fun deletePost(id: Long) {
        val post = postRepository.findByIdOrNull(id) ?: throw Exception("게시글 정보 없음.")
        checkAuthority(post)
        postRepository.delete(post)
    }

    override fun getPost(id: Long): PostInfoResponse {
        val post = postRepository.findByIdOrNull(id) ?: throw Exception("게시글 정보 없음.")
        return PostInfoResponse.of(post)
    }

    override fun getPosts(pageable: Pageable, postSearchCondition: PostSearchCondition): List<PostDto> {
        TODO("Not yet implemented")
    }

    private fun checkAuthority(post: Post) {
        if(!post.writer!!.userName.equals(SecurityUtil.getSignInUsername()))
            throw Exception("수정 권한 없음.")
    }
}