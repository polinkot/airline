package com.example.airline.flight.usecase.order

import com.example.airline.flight.domain.order.Order
import com.example.airline.flight.domain.order.OrderId

interface OrderExtractor {

    fun getById(id: OrderId): Order?

    fun getAll(): List<Order>
}