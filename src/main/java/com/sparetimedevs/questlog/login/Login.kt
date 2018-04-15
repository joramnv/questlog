package com.sparetimedevs.questlog.login

import org.springframework.hateoas.ResourceSupport

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Login @JsonCreator constructor(
        @param:JsonProperty("emailAddress")
        val emailAddress: String? = null,

        @param:JsonProperty("password")
        val password: String? = null
) : ResourceSupport()
