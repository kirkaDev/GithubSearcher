package com.desiredsoftware.githubsearcher

import com.desiredsoftware.utils.getRussianDateFormat
import org.junit.Test

import org.junit.Assert.*

class UtilsUnitTests {
    @Test
    fun russianDateFormat_isCorrect() {
        val inDate = "2021-05-18T07:22:23Z"

        val outDate = getRussianDateFormat(inDate)
        val expectedDate = "18.05.2021"

        assertEquals(expectedDate, outDate)
    }
}