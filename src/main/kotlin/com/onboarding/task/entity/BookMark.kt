package com.onboarding.task.entity

import com.onboarding.task.enum.BookMarkStatus
import jakarta.persistence.*

@Entity
class BookMark(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    val board: Board,

    @Enumerated(EnumType.STRING)
    var status: BookMarkStatus,

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    val id: Long? = null
) {

  fun markPost() {
    this.status = BookMarkStatus.MARKED
  }

  fun unmarkPost() {
    this.status = BookMarkStatus.UNMARKED
  }
}