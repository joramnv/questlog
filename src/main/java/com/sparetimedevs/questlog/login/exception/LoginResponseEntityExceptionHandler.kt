package com.sparetimedevs.questlog.login.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class LoginResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [EmailAddressPasswordDoNotMatchException::class, IllegalArgumentException::class, IllegalStateException::class])
    protected fun handleConflict(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = "This should be application specific"
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.CONFLICT, request)
    }
}
