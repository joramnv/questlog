package com.sparetimedevs.questlog.userpassword

import com.sparetimedevs.questlog.user.User
import java.io.Serializable
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "USER_PASSWORD")
data class UserPassword(
        @Id
        @Column(name = "ID", nullable = false, updatable = false)
        val id: UUID = UUID.randomUUID(),

        @OneToOne(targetEntity = User::class, optional = false)
        @JoinColumn(name = "USER_EMAIL_ADDRESS", referencedColumnName = "EMAIL_ADDRESS", nullable = false)
        val user: User,

        @Column(nullable = false)
        val password: String
) : Serializable
