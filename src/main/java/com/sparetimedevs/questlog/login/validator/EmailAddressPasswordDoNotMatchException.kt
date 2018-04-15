package com.sparetimedevs.questlog.login.validator

class EmailAddressPasswordDoNotMatchException(override var message:String): RuntimeException(message)
