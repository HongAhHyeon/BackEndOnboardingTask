package com.onboarding.task.service

import com.onboarding.task.dto.request.BoardCreateRequest
import com.onboarding.task.dto.request.BoardUpdateRequest
import com.onboarding.task.dto.request.BookMarkRequest
import com.onboarding.task.dto.response.BoardInfoBriefResponse
import com.onboarding.task.dto.response.BoardInfoResponse
import com.onboarding.task.dto.response.BoardPagingResponse
import com.onboarding.task.entity.condition.BoardSearchCondition
import org.springframework.data.domain.Pageable

interface BookMarkService {

    fun markBoard(req : BookMarkRequest)
    fun unmarkBoard(req: BookMarkRequest)

}