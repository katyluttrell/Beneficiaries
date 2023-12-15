package com.katy.beneficiaries.util

import com.katy.beneficiaries.model.Address
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

internal class AddressJsonParserTest {

    private val addressJsonParser = AddressJsonParser()

    @Test
    fun testParseAddressAllFields() {
        val testJson = JSONObject(
            """
            {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": "Apt 10",
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
        """.trimIndent()
        )
        val expectedResult = Address(
            "8515 E Orchard Rd",
            "Apt 10",
            "Greenwood Village",
            "80111",
            "CO",
            "US"
        )

        assertEquals(expectedResult, addressJsonParser.parseAddress(testJson))
    }

    @Test
    fun testParseAddressSomeMissingAndNull() {
        val testJson = JSONObject(
            """
            {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
    }
        """.trimIndent()
        )
        val expectedResult = Address(
            "8515 E Orchard Rd",
            null,
            "Greenwood Village",
            "80111",
            "CO",
            null
        )

        assertEquals(expectedResult, addressJsonParser.parseAddress(testJson))
    }

    @Test
    fun testParseAddressTooManyNull(){
        val testJson = JSONObject("""
            {
      "firstLineMailing": null,
      "scndLineMailing": null,
      "city": null,
      "zipCode": null,
      "stateCode": null,
      "country": null
    }
        """.trimIndent())

        assertEquals(null, addressJsonParser.parseAddress(testJson))
    }

    @Test
    fun testParseAddressTooManyMissing(){
        val testJson = JSONObject("""
            {
      "firstLineMailing": "123 Main Street",
    }
        """.trimIndent())

        assertEquals(null, addressJsonParser.parseAddress(testJson))
    }

}