package com.onboarding.task.repository.impl

import com.onboarding.task.entity.Board
import com.onboarding.task.entity.QPost.post
import com.onboarding.task.entity.QUser.user
import com.onboarding.task.entity.condition.BoardSearchCondition
import com.onboarding.task.repository.CustomPostRepository
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.util.StringUtils

class CustomPostRepositoryImpl (
    val query: JPAQueryFactory
) : CustomPostRepository {
    override fun search(boardSearchCondition: BoardSearchCondition, pageable: Pageable): Page<Board> {
        val content = query.selectFrom(post)
            .where(
                contentHasStr(boardSearchCondition.content),
                titleHasStr(boardSearchCondition.title)
            )
            .leftJoin(post.writer, user)
            .fetchJoin()
            .orderBy(post.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize as Long)
            .fetch()

        val countQuery = query.selectFrom(post)
            .where(
                contentHasStr(boardSearchCondition.content),
                titleHasStr(boardSearchCondition.title)
            )

        return PageImpl(content, pageable, countQuery.fetch().size as Long)
    }

    private fun contentHasStr(content: String) : BooleanExpression? {
        return if(StringUtils.hasLength(content)) post.content.contains(content) else null
    }
    private fun titleHasStr(title: String) : BooleanExpression? {
        return if(StringUtils.hasLength(title)) post.title.contains(title) else null
    }

}