package com.example.airline.flight.usecase.order

class GetOrdersUseCase(private val extractor: OrderExtractor) : GetOrders {
    override fun execute(): List<OrderInfo> {
        return extractor.getAll().map {
            OrderInfo(
                    id = it.id,
                    created = it.created,
                    email = it.email
            )
        }.toList()
    }
}