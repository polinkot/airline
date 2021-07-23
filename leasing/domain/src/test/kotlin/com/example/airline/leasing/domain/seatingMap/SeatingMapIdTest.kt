package com.example.airline.leasing.domain.seatingMap

import com.example.airline.leasing.domain.seatingmap.SeatingMapId
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import kotlin.random.Random

class SeatingMapIdTest {

    @Test
    fun `check equality`() {
        val id = Random.nextLong()

        val seatingMapId1 = SeatingMapId(id)
        val seatingMapId2 = SeatingMapId(id)
        seatingMapId1 shouldBe seatingMapId2
        seatingMapId1 shouldNotBeSameInstanceAs seatingMapId2
        seatingMapId1.value shouldBe seatingMapId2.value
    }
}