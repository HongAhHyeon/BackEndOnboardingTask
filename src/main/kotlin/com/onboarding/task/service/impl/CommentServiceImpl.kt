package com.onboarding.task.service.impl

import com.onboarding.task.dto.request.CommentCreateRequest
import com.onboarding.task.dto.request.CommentUpdateRequest
import com.onboarding.task.dto.response.CommentInfoResponse
import com.onboarding.task.entity.Comment
import com.onboarding.task.repository.CommentRepository
import com.onboarding.task.repository.BoardRepository
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.CommentService
import com.onboarding.task.util.SecurityUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CommentServiceImpl(
    val commentRepository: CommentRepository,
    val boardRepository: BoardRepository,
    val memberRepository: MemberRepository
) : CommentService {

    override fun createComment(boardId: Long, req: CommentCreateRequest) {
        val comment = req.toEntity()
//        comment.confirmWriter(memberRepository.findByMemberEmail(SecurityUtil.getSignInUsername()) ?: throw Exception("사용자 정보 없음."))
        comment.confirmWriter(memberRepository.findByMemberEmail("test@test.com") ?: throw Exception("사용자 정보 없음."))
        comment.confirmPost(boardRepository.findByIdOrNull(boardId) ?: throw Exception("게시글 정보 없음."))

        commentRepository.save(comment)
    }

    override fun updateComment(id: Long, req: CommentUpdateRequest) {
        val comment = commentRepository.findByIdOrNull(id) ?: throw Exception("댓글 정보 없음.")
        if(!comment.writer?.memberName.equals(SecurityUtil.getSignInUsername())) throw Exception ("댓글 수정 권한 없음.")

        comment.content = req.content
    }

    override fun getComment(id: Long): Comment {
        return commentRepository.findByIdOrNull(id) ?: throw Exception ("댓글 정보 없음.")
    }

    override fun getComments(boardId: Long): MutableList<CommentInfoResponse> {
        val comments = commentRepository.findCommentsByBoardId(boardId)
        return comments.stream().map { CommentInfoResponse.of(it) }.toList()
    }

    override fun deleteComment(id: Long) {
        val comment = commentRepository.findByIdOrNull(id) ?: throw Exception ("댓글 정보 없음.")
        if(!comment.writer?.memberName.equals(SecurityUtil.getSignInUsername())) throw Exception ("댓글 삭제 권한 없음.")

        comment.delete()
        commentRepository.delete(comment)
    }
}