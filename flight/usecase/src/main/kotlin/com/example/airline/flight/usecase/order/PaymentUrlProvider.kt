package com.example.airline.flight.usecase.order

import com.example.airline.flight.domain.order.OrderId
import com.example.airline.flight.domain.ticket.Price
import java.net.URL

interface PaymentUrlProvider {
    fun provideUrl(orderId: OrderId, price: Price): URL
}