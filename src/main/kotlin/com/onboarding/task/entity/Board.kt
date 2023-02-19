package com.onboarding.task.entity

import jakarta.persistence.*

@Entity
class Board (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: Member? = null,

    var title: String,

    @Lob
    var content: String,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "board_id")
    val id: Long? = null


) : BaseTimeEntity() {

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun confirmWriter(writer: Member) {
        this.writer = writer
        writer.boards.add(this)
    }
    fun addComment(comment: Comment) {
        comments.add(comment)
    }
}