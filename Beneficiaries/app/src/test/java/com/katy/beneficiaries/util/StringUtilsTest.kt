package com.katy.beneficiaries.util

import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

internal class StringUtilsTest {
    private val stringUtils = StringUtils()

    @Test
    fun testDateParseSuccess() {
        val pattern = "MMddyyyy"
        val testString = "05012008"
        val expectedDate = LocalDate.of(2008, 5, 1)

        assertEquals(expectedDate, stringUtils.createDateFromString(testString, pattern))
    }

    @Test
    fun testDateParseBadPattern() {
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) } returns 0
        val pattern = "badpattern"
        val testString = "05012008"

        assertEquals(null, stringUtils.createDateFromString(testString, pattern))
        verify { Log.e("StringUtils", "Unable to create date formatter from pattern badpattern") }
    }

    @Test
    fun testDateParseBadDateString() {
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) } returns 0
        val pattern = "MMddyyyy"
        val testString = "badstring"

        assertEquals(null, stringUtils.createDateFromString(testString, pattern))
        verify { Log.e("StringUtils", "Unable to parse date badstring") }
    }

    @Test
    fun testFormatValidCensoredSSN(){
        val ssnString = "XXXXX1234"
        val expectedResult = "XXX-XX-1234"

        assertEquals(expectedResult, stringUtils.formatAndValidateSSN(ssnString))
    }

    @Test
    fun testFormatValidUncensoredSSN(){
        val ssnString = "987651234"
        val expectedResult = "XXX-XX-1234"

        assertEquals(expectedResult, stringUtils.formatAndValidateSSN(ssnString))
    }

    @Test
    fun testFormatInvalidSSN(){
        val ssnString = "invalid1244"

        assertEquals(null, stringUtils.formatAndValidateSSN(ssnString))
    }

    @Test
    fun testFormatValidPhoneNumber(){
        val testString = "1234567890"
        val expectedResult = "123-456-7890"

        assertEquals(expectedResult, stringUtils.formatAndValidatePhoneNumber(testString))
    }

    @Test
    fun testFormatTooLongPhoneNumber(){
        val testString = "123456789076930790"

        assertEquals(null, stringUtils.formatAndValidatePhoneNumber(testString))
    }

    @Test
    fun testFormatNotNumberPhoneNumber(){
        val testString = "123-456789"

        assertEquals(null, stringUtils.formatAndValidatePhoneNumber(testString))
    }


}