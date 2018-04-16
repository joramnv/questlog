package com.sparetimedevs.questlog.user

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.NaturalId
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator

@Entity
data class User(
        @NaturalId
        @Column(name = "EMAIL_ADDRESS", unique = true, nullable = false)
        val emailAddress: String
) : Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-sequence-generator")
	@SequenceGenerator(name = "user-sequence-generator", sequenceName = "USER_SEQUENCE")
	private var id: Long = -1
}
