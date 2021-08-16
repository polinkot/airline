package com.example.airline.flight.usecase.order

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.common.CreateEmailError
import com.example.airline.common.types.common.Email
import com.example.airline.flight.domain.order.*
import com.example.airline.flight.domain.ticket.TicketId

data class CreateOrderRequest internal constructor(
        val email: Email,
        val orderItems: Set<OrderItem>
) {
    companion object {
        fun from(
                email: String,
                items: Set<OrderItemData>
        ): Either<InvalidOrderParameters, CreateOrderRequest> {
            if (items.isNullOrEmpty()) {
                return InvalidOrderParameters("Empty order").left()
            }

            val orderItems = items.map {
                tupled(
                        if (it.ticketId <= 0) {
                            InvalidOrderParameters("Non positive ticket id").left()
                        } else {
                            TicketId(it.ticketId).right()
                        },
                        FullName.from(it.fullName).mapLeft { it.toError() },
                        Passport.from(it.passport).mapLeft { it.toError() }
                ).map { sourceItem -> OrderItem.from(sourceItem.a, sourceItem.b, sourceItem.c) }
            }.map {
                it.mapLeft { e -> return@from e.left() }
            }.mapNotNull { it.orNull() }
                    .toSet()

            return Email.from(email).mapLeft { it.toError() }
                    .map { CreateOrderRequest(it, orderItems) }
        }
    }
}

data class OrderItemData(val ticketId: Long, val fullName: String, val passport: String)

data class InvalidOrderParameters(val message: String)

fun CreateFullNameError.toError() = InvalidOrderParameters("Empty full name")
fun CreatePassportError.toError() = InvalidOrderParameters("Empty passport")
fun CreateEmailError.toError() = InvalidOrderParameters("Invalid email")