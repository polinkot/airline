package com.example.airline.leasing.domain.seatingMap

import com.example.airline.leasing.domain.seatingmap.NonPositiveSeatingRowError
import com.example.airline.leasing.domain.seatingmap.SeatingRow
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class SeatingRowTest {

    @Test
    fun `create seatingRow - success`() {
        val seatingRow = 10
        val result = SeatingRow.from(seatingRow)

        result shouldBeRight {
            it.value shouldBe seatingRow
        }
    }

    @Test
    fun `create seatingRow - zero`() {
        val result = SeatingRow.from(0)
        result shouldBeLeft NonPositiveSeatingRowError
    }

    @Test
    fun `create seatingRow - negative`() {
        val result = SeatingRow.from(-500)
        result shouldBeLeft NonPositiveSeatingRowError
    }
}