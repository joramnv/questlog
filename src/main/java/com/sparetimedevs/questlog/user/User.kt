package com.sparetimedevs.questlog.user

import com.fasterxml.jackson.annotation.JsonIdentityReference
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
        @JsonIdentityReference(alwaysAsId = true)
        val emailAddress: String
) : Serializable {
	constructor() : this(""	)

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-sequence-generator")
	@SequenceGenerator(name = "user-sequence-generator", sequenceName = "USER_SEQUENCE")
	@field:JsonIgnore(value = true)
	var id: Long = -1
}
