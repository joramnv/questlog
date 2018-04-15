package com.sparetimedevs.questlog.quest

import com.sparetimedevs.questlog.user.User

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator

@Entity
data class Quest(
        @ManyToOne(targetEntity = User::class, optional = false)
        @JoinColumn(name = "USER_EMAIL_ADDRESS", referencedColumnName = "EMAIL_ADDRESS", nullable = false)
        val user: User,

        val name: String,

        val description: String,

        val achievementPoint: Long
) {
    constructor() : this(User(), "", "", 0	)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quest-sequence-generator")
    @SequenceGenerator(name = "quest-sequence-generator", sequenceName = "QUEST_SEQUENCE")
    var id: Long = -1
}
