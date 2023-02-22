package com.onboarding.task.service.impl

import com.onboarding.task.entity.Board
import com.onboarding.task.dto.request.BoardCreateRequest
import com.onboarding.task.dto.request.BoardUpdateRequest
import com.onboarding.task.dto.response.BoardInfoBriefResponse
import com.onboarding.task.dto.response.BoardInfoResponse
import com.onboarding.task.dto.response.BoardPagingResponse
import com.onboarding.task.entity.condition.BoardSearchCondition
import com.onboarding.task.repository.BoardRepository
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.BoardService
import com.onboarding.task.util.SecurityUtil
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository
) : BoardService {
    @Transactional
    override fun createBoard(req: BoardCreateRequest) {
        val board = req.toEntity()

//        post.confirmWriter(memberRepository.findByMemberEmail(SecurityUtil.getSignInUsername()) ?: throw IllegalArgumentException("사용자 정보 없음."))
        board.confirmWriter(memberRepository.findByMemberEmail("bbb@test.com") ?: throw IllegalArgumentException("사용자 정보 없음."))
        boardRepository.save(board)
    }
    @Transactional
    override fun updateBoard(req: BoardUpdateRequest) {
        val board = boardRepository.findByIdOrNull(req.id) ?: throw Exception("게시글 정보 없음.")
//        checkAuthority(board)
        board.updateTitle(req.title)
        board.updateContent(req.content)
    }
    @Transactional
    override fun deleteBoard(id: Long) {
        val board = boardRepository.findByIdOrNull(id) ?: throw Exception("게시글 정보 없음.")
//        checkAuthority(board)
        boardRepository.delete(board)
    }
    @Transactional(readOnly = true)
    override fun getBoard(id: Long): BoardInfoResponse {
        val board = boardRepository.findByIdOrNull(id) ?: throw Exception("게시글 정보 없음.")
        return BoardInfoResponse.of(board)
    }

    @Transactional(readOnly = true)
    override fun getBoards(pageable: Pageable, boardSearchCondition: BoardSearchCondition): BoardPagingResponse {
        return BoardPagingResponse.of(boardRepository.search(boardSearchCondition, pageable))
    }

    @Transactional(readOnly = true)
    override fun getBoardsSimple(): MutableList<BoardInfoBriefResponse> {
        return boardRepository.getBoardsBrief()
    }
    @Transactional(readOnly = true)
    override fun getMyBoards(): MutableList<BoardInfoBriefResponse> {
        return boardRepository.getMyBoards()
    }

    override fun getMyBookMark(): MutableList<BoardInfoBriefResponse> {
        return boardRepository.getMyBookMark()
    }

    private fun checkAuthority(board: Board) {
        if(!board.writer!!.memberName.equals(SecurityUtil.getSignInUsername()))
            throw Exception("수정 권한 없음.")
    }
}