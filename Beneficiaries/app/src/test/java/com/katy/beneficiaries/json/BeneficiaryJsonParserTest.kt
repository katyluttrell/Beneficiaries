package com.katy.beneficiaries.json

import android.util.Log
import com.katy.beneficiaries.model.Address
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.model.Designation
import com.katy.beneficiaries.util.StringUtils
import io.mockk.every
import io.mockk.mockkStatic
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

internal class BeneficiaryJsonParserTest {

    private val beneficiaryJsonParser = BeneficiaryJsonParser(StringUtils(), AddressJsonParser())

    @Test
    fun testParseBeneficiarySuccess() {
        val testString = JSONObject(
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
        val expectedResult = Beneficiary(
            "Smith",
            "John",
            Designation.PRIMARY,
            "Spouse",
            "XXX-XX-3333",
            LocalDate.of(1979, 4, 20),
            "D",
            "303-555-5555",
            Address(
                "8515 E Orchard Rd", null, "Greenwood Village", "80111", "CO", "US"
            )
        )

        val result = beneficiaryJsonParser.parseBeneficiary(testString)
        assertEquals(expectedResult, result)
    }

    @Test
    fun testParseBeneficiaryMissingValuesSuccess() {
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) } returns 0
        val testString = JSONObject(
            """
              {
    "lastName": "Smith",
    "firstName": "John",
    "designationCode": "P",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201971239",
    "middleName": "",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
    }}
        """.trimIndent()
        )
        val expectedResult = Beneficiary(
            "Smith",
            "John",
            Designation.PRIMARY,
            null,
            "XXX-XX-3333",
            null,
            null,
            "303-555-5555",
            null
        )

        val result = beneficiaryJsonParser.parseBeneficiary(testString)
        assertEquals(expectedResult, result)
    }

    @Test
    fun testParseBeneficiaryTooManyMissingValues() {
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) } returns 0
        val testString = JSONObject(
            """
              {
    "firstName": "",
    "designationCode": "P",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201971239",
    "middleName": "D",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
    }}
        """.trimIndent()
        )

        val result = beneficiaryJsonParser.parseBeneficiary(testString)
        assertEquals(null, result)
    }
}