package com.example.airline.common.types.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.common.CreateEmailError.EmptyEmailError
import com.example.airline.common.types.common.CreateEmailError.WrongFormatEmaiError
import java.util.regex.Pattern

data class Email internal constructor(
        val value: String
) : ValueObject {

    companion object {
        val EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")

        fun from(email: String): Either<CreateEmailError, Email> {
            return when {
                email.isBlank() -> EmptyEmailError.left()
                !EMAIL_ADDRESS_PATTERN.matcher(email).matches() -> WrongFormatEmaiError.left()
                else -> Email(email).right()
            }
        }
    }
}

sealed class CreateEmailError {
    object EmptyEmailError : CreateEmailError()
    object WrongFormatEmaiError : CreateEmailError()
}