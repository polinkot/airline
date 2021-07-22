package com.example.airline.common.types.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.common.CreateEmailError.EmptyString
import com.example.airline.common.types.common.CreateEmailError.WrongFormat
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

        fun from(value: String): Either<CreateEmailError, Email> {
            return when {
                value.isBlank() -> EmptyString.left()
                !EMAIL_ADDRESS_PATTERN.matcher(value).matches() -> WrongFormat.left()
                else -> Email(value).right()
            }
        }
    }
}

sealed class CreateEmailError {
    object EmptyString : CreateEmailError()
    object WrongFormat : CreateEmailError()
}