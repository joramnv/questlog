package com.sparetimedevs.questlog.userpassword

import com.sparetimedevs.questlog.user.User

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import java.io.Serializable
import javax.persistence.SequenceGenerator

@Entity
@Table(name = "USER_PASSWORD")
data class UserPassword(
        @OneToOne(targetEntity = User::class, optional = false)
        @JoinColumn(name = "USER_EMAIL_ADDRESS", referencedColumnName = "EMAIL_ADDRESS", nullable = false)
        val user: User,

        @Column(nullable = false)
        val password: String? = null
) : Serializable {
    constructor() : this(User(), ""	)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-password-sequence-generator")
    @SequenceGenerator(name = "user-password-sequence-generator", sequenceName = "USER_PASSWORD_SEQUENCE")
    var id: Long = -1
}
