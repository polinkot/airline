package com.example.airline.flight.domain.order

import com.example.airline.common.types.base.DomainEvent

data class OrderCreatedDomainEvent(val orderId: OrderId) : DomainEvent()
data class OrderPaidDomainEvent(val orderId: OrderId) : DomainEvent()
data class OrderCancelledDomainEvent(val orderId: OrderId) : DomainEvent()
