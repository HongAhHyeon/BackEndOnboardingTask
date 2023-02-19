package com.onboarding.task.repository.impl

import com.onboarding.task.dto.response.BoardInfoBriefResponse
import com.onboarding.task.entity.Board
import com.onboarding.task.entity.QBoard.board
import com.onboarding.task.entity.QMember.member
import com.onboarding.task.entity.condition.BoardSearchCondition
import com.onboarding.task.repository.CustomPostRepository
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.util.StringUtils

class CustomPostRepositoryImpl (
    val query: JPAQueryFactory
) : CustomPostRepository {
    override fun search(boardSearchCondition: BoardSearchCondition, pageable: Pageable): Page<Board> {
        val content = query.selectFrom(board)
            .where(
                contentHasStr(boardSearchCondition.content),
                titleHasStr(boardSearchCondition.title)
            )
            .leftJoin(board.writer, member)
            .fetchJoin()
            .orderBy(board.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize as Long)
            .fetch()

        val countQuery = query.selectFrom(board)
            .where(
                contentHasStr(boardSearchCondition.content),
                titleHasStr(boardSearchCondition.title)
            )

        return PageImpl(content, pageable, countQuery.fetch().size as Long)
    }

    override fun getBoardsBrief(): MutableList<BoardInfoBriefResponse> {
        val boards = query.selectFrom(board)
            .join(board.writer, member)
            .fetchJoin()
            .fetch()

        return boards.stream().map { BoardInfoBriefResponse.of(it) }.toList()
    }

    private fun contentHasStr(content: String) : BooleanExpression? {
        return if(StringUtils.hasLength(content)) board.content.contains(content) else null
    }
    private fun titleHasStr(title: String) : BooleanExpression? {
        return if(StringUtils.hasLength(title)) board.title.contains(title) else null
    }

}