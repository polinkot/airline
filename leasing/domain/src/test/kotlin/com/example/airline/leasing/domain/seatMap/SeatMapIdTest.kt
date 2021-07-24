package com.example.airline.leasing.domain.seatMap

import com.example.airline.leasing.domain.seatmap.SeatMapId
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import kotlin.random.Random

class SeatMapIdTest {

    @Test
    fun `check equality`() {
        val id = Random.nextLong()

        val seatMapId = SeatMapId(id)
        val seatMapId2 = SeatMapId(id)
        seatMapId shouldBe seatMapId2
        seatMapId shouldNotBeSameInstanceAs seatMapId2
        seatMapId.value shouldBe seatMapId2.value
    }
}