package com.katy.beneficiaries.util

import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

internal class BeneficiaryJsonParserTest {

    val beneficiaryJsonParser = BeneficiaryJsonParser()

    @Test
    fun testGetJSONArrayOrNullSuccess() {
        val testString = """
           [
    {
        "name": "John",
        "age": 30,
        "city": "New York"
    },
    {
        "name": "Alice",
        "age": 25,
        "city": "Los Angeles"
    },
    {
        "name": "Bob",
        "age": 35,
        "city": "Chicago"
    }
]
       """.trimIndent()

        val expectedResults = arrayOf(
            JSONObject(
                """{
        "name": "John",
        "age": 30,
        "city": "New York"
    }"""
            ),
            JSONObject(
                """{
        "name": "Alice",
        "age": 25,
        "city": "Los Angeles"
    }"""
            ),
            JSONObject(
                """    {
        "name": "Bob",
        "age": 35,
        "city": "Chicago"
    }"""
            )
        )

        val result = beneficiaryJsonParser.getJSONArrayOrNull(testString)
        assertEquals(3, result?.length())
        assertEquals(expectedResults[0].toString(), result?.getJSONObject(0).toString())
        assertEquals(expectedResults[1].toString(), result?.getJSONObject(1).toString())
        assertEquals(expectedResults[2].toString(), result?.getJSONObject(2).toString())
    }

    @Test
    fun testGetJSONArrayOrNullMalformedJson() {
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) } returns 0
        val testString = """
           [
    {
        "name": "John",
        "age": 30,
        "city": "New York
    },
    
        "name": "Alice",
        "age": 25,
        "city": "Los Angeles"
    },
    {
        "name": ,
        "age": 35,
        "city": "Chicago"
    }
]
       """.trimIndent()

        val result = beneficiaryJsonParser.getJSONArrayOrNull(testString)
        assertEquals(null, result)
    }
}