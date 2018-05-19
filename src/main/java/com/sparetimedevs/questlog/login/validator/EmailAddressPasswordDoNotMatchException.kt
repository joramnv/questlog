package com.sparetimedevs.questlog.login.validator

class EmailAddressPasswordDoNotMatchException(override val message: String): RuntimeException(message)
