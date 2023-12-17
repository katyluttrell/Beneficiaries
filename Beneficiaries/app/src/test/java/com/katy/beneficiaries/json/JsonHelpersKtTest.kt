package com.katy.beneficiaries.json

import android.util.Log
import com.katy.beneficiaries.model.BeneficiaryKeys
import com.katy.beneficiaries.util.getJSONArrayOrNull
import com.katy.beneficiaries.util.getNestedObjectOrNull
import com.katy.beneficiaries.util.getStringOrNull
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

internal class JsonHelpersKtTest {

    @After
    fun cleanup(){
        unmockkAll()
    }
    @Test
    fun testGetStringOrNullSuccess() {
        val testObject = JSONObject(
            """
                {
    "lastName": "Smith",
    "firstName": "John",
    "designationCode": "P",
    "beneType": "Spouse",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201979",
    "middleName": "D",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }}
            """.trimIndent()
        )
        val result = testObject.getStringOrNull(BeneficiaryKeys.FIRST_NAME)
        assertEquals("John", result)
    }

    @Test
    fun testGetStringOrNullBadKey() {
        val testObject = JSONObject(
            """
                {
    "lastName": "Smith",
    "firstName": "John",
    "designationCode": "P",
    "beneType": "Spouse",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201979",
    "middleName": "D",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }}
            """.trimIndent()
        )
        val result = testObject.getStringOrNull("BadKey")
        assertEquals(null, result)
    }

    @Test
    fun testGetStringOrNullEmpty() {
        val testObject = JSONObject(
            """
                {
    "lastName": "Smith",
    "firstName": "",
    "designationCode": "P",
    "beneType": "Spouse",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201979",
    "middleName": "D",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }}
            """.trimIndent()
        )
        val result = testObject.getStringOrNull(BeneficiaryKeys.FIRST_NAME)
        assertEquals(null, result)
    }

    @Test
    fun testGetStringOrNullWordNull() {
        val testObject = JSONObject(
            """
                {
    "lastName": "Smith",
    "firstName": "John",
    "designationCode": "P",
    "beneType": "Spouse",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201979",
    "middleName": null,
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }}
            """.trimIndent()
        )
        val result = testObject.getStringOrNull(BeneficiaryKeys.MIDDLE_NAME)
        assertEquals(null, result)
    }

    @Test
    fun testGetNestedObjectOrNullSuccess() {
        val testObject = JSONObject(
            """
                {
    "lastName": "Smith",
    "firstName": "John",
    "designationCode": "P",
    "beneType": "Spouse",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201979",
    "middleName": "D",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }}
            """.trimIndent()
        )
        val expectedResult = JSONObject(
            """{
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    } 
            """.trimIndent()
        )

        val result = testObject.getNestedObjectOrNull(BeneficiaryKeys.ADDRESS)
        assertEquals(expectedResult.toString(), result.toString())
    }

    @Test
    fun testGetNestedObjectOrNullBadKey() {
        val testObject = JSONObject(
            """
                {
    "lastName": "Smith",
    "firstName": "John",
    "designationCode": "P",
    "beneType": "Spouse",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201979",
    "middleName": "D",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }}
            """.trimIndent()
        )

        val result = testObject.getNestedObjectOrNull("BadKey")
        assertEquals(null, result)
    }

    @Test
    fun testGetNestedObjectOrNullEmpty() {
        val testObject = JSONObject(
            """
                {
    "lastName": "Smith",
    "firstName": "John",
    "designationCode": "P",
    "beneType": "Spouse",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201979",
    "middleName": "D",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
    }}
            """.trimIndent()
        )

        val result = testObject.getNestedObjectOrNull(BeneficiaryKeys.ADDRESS)
        assertEquals(null, result)
    }

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
            ), JSONObject(
                """{
        "name": "Alice",
        "age": 25,
        "city": "Los Angeles"
    }"""
            ), JSONObject(
                """    {
        "name": "Bob",
        "age": 35,
        "city": "Chicago"
    }"""
            )
        )

        val result = testString.getJSONArrayOrNull()
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

        val result = testString.getJSONArrayOrNull()
        assertEquals(null, result)
    }

    @Test
    fun testGetJSONArrayOrNullEmpty() {
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) } returns 0
        val testString = """
           []
       """.trimIndent()

        val result = testString.getJSONArrayOrNull()
        assertEquals(null, result)
    }
}