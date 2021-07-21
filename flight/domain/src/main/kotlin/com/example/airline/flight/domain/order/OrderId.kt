package com.example.airline.flight.domain.order

data class OrderId(val value: Long)

interface OrderIdGenerator {
    fun generate(): OrderId
}