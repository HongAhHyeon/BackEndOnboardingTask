package com.onboarding.task.service.impl

import com.onboarding.task.entity.Board
import com.onboarding.task.dto.request.BoardCreateRequest
import com.onboarding.task.dto.request.BoardUpdateRequest
import com.onboarding.task.dto.request.BookMarkRequest
import com.onboarding.task.dto.response.BoardInfoBriefResponse
import com.onboarding.task.dto.response.BoardInfoResponse
import com.onboarding.task.dto.response.BoardPagingResponse
import com.onboarding.task.entity.condition.BoardSearchCondition
import com.onboarding.task.enum.BookMarkStatus
import com.onboarding.task.repository.BoardRepository
import com.onboarding.task.repository.BookMarkRepository
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.BoardService
import com.onboarding.task.service.BookMarkService
import com.onboarding.task.util.SecurityUtil
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
@Service
class BookMarkServiceImpl(
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository,
    private val bookMarkRepository: BookMarkRepository
) : BookMarkService {

    @Transactional
    override fun markBoard(req: BookMarkRequest) {
        val board = boardRepository.findByIdOrNull(req.boardId) ?: throw Exception("게시글 정보 없음.")
        val member = memberRepository.findByIdOrNull(req.memberId) ?: throw Exception("사용자 정보 없음.")
        if(bookMarkRepository.findByBoardIdAndMemberIdAndStatus(req.boardId, req.memberId, BookMarkStatus.MARKED) != null) {
            throw Exception("이미 찜한 게시글입니다.")
        }else if(bookMarkRepository.findByBoardIdAndMemberIdAndStatus(req.boardId, req.memberId, BookMarkStatus.UNMARKED) != null) {
            member.remarkBoard(req.boardId)
        }else{
            member.markBoard(board)
        }

    }

    @Transactional
    override fun unmarkBoard(req: BookMarkRequest) {
        val member = memberRepository.findByIdOrNull(req.memberId) ?: throw Exception("사용자 정보 없음.")
        member.unmarkBoard(req.boardId)
    }


}