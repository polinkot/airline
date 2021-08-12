package com.example.airline.flight.usecase.order

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.common.CreateEmailError
import com.example.airline.common.types.common.Email
import com.example.airline.flight.domain.order.*
import com.example.airline.flight.domain.ticket.CreatePriceError
import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.TicketId
import java.math.BigDecimal
import java.time.OffsetDateTime

data class CreateOrderRequest internal constructor(
        val created: OffsetDateTime,
        val email: Email,
        val orderItems: Set<OrderItem>,
        val price: Price
) {
    companion object {
        fun from(
                created: OffsetDateTime,
                email: String,
                items: Set<OrderItemData>,
                price: BigDecimal
        ): Either<InvalidOrderParameters, CreateOrderRequest> {

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

            return tupled(
                    Email.from(email).mapLeft { it.toError() },
                    Price.from(price).mapLeft { it.toError() },
            ).map { params ->
                CreateOrderRequest(created, params.a, orderItems, params.b)
            }
        }
    }
}

data class OrderItemData(val ticketId: Long, val fullName: String, val passport: String)

data class InvalidOrderParameters(val message: String)

fun CreateFullNameError.toError() = InvalidOrderParameters("Empty full name")
fun CreatePassportError.toError() = InvalidOrderParameters("Empty passport")
fun CreateEmailError.toError() = InvalidOrderParameters("Invalid email")
fun CreatePriceError.toError() = InvalidOrderParameters("Invalid price")