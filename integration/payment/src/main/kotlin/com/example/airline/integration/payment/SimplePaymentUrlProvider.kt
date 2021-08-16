package com.example.airline.integration.payment

import com.example.airline.flight.domain.order.OrderId
import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.usecase.order.PaymentUrlProvider
import java.net.URL

class SimplePaymentUrlProvider(private val currentUrl: URL) : PaymentUrlProvider {
    override fun provideUrl(orderId: OrderId, price: Price): URL {
        return URL("$currentUrl/payment?orderId=${orderId.value}&price=${price.value}")
    }
}