package com.onboarding.task.dto.request

class BoardUpdateRequest (
    val id: Long,
    val title: String,
    val content: String
){
    constructor() : this(0L, "", "")
}