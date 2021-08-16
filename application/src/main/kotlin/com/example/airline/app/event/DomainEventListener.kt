package com.example.airline.app.event

import com.example.airline.common.types.base.DomainEvent
import kotlin.reflect.KClass

interface DomainEventListener<T : DomainEvent> {

    fun eventType(): KClass<T>

    fun handle(event: T)
}
