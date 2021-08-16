package com.example.airline.flight.usecase.order

import com.example.airline.flight.domain.order.Order

interface OrderPersister {
    fun save(order: Order)
}