package com.example.airline.leasing.domain.seatMap

import com.example.airline.leasing.domain.seatmap.NonPositiveSeatRowError
import com.example.airline.leasing.domain.seatmap.Row
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class RowTest {

    @Test
    fun `create seatRow - success`() {
        val row = 10
        val result = Row.from(row)

        result shouldBeRight {
            it.value shouldBe row
        }
    }

    @Test
    fun `create seatRow - zero`() {
        val result = Row.from(0)
        result shouldBeLeft NonPositiveSeatRowError
    }

    @Test
    fun `create seatRow - negative`() {
        val result = Row.from(-500)
        result shouldBeLeft NonPositiveSeatRowError
    }
}