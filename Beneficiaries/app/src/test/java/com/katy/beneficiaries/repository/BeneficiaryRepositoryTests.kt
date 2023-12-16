package com.katy.beneficiaries.repository

import android.content.Context
import android.util.Log
import com.katy.beneficiaries.json.AddressJsonParser
import com.katy.beneficiaries.json.BeneficiaryJsonParser
import com.katy.beneficiaries.json.JsonLoader
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.model.Designation
import com.katy.beneficiaries.util.Constants
import com.katy.beneficiaries.util.StringUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate


internal class BeneficiaryRepositoryTests{

    val mockContext = mockk<Context>()
    val jsonParser = BeneficiaryJsonParser(StringUtils(), AddressJsonParser())
    val mockJsonLoader = mockk<JsonLoader>()
    val testCoroutineScheduler = TestCoroutineScheduler()
    val testDispatcher = StandardTestDispatcher(testCoroutineScheduler)
    val respository = BeneficiaryRepository(jsonParser, mockJsonLoader, testDispatcher)

    @Before
    fun setup(){
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) } returns 0
    }

    @Test
    fun testGetBeneficiaryDataSuccess() = runTest(testCoroutineScheduler){
        setupSuccessfulJsonLoader()

        val list = respository.getBeneficiaryData(mockContext)?.awaitAll()
        assertEquals(13, list?.size)
    }

    fun setupSuccessfulJsonLoader(){
        coEvery { mockJsonLoader.loadJson(mockContext, Constants.BENEFICIARIES_FILE_NAME) } returns
                """
                    [
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
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Jane",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX4664",
    "dateOfBirth": "01112012",
    "middleName": "E",
    "phoneNumber": "3034455555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Mary",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX3645",
    "dateOfBirth": "02122013",
    "middleName": "E",
    "phoneNumber": "2035557558",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "David",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX7652",
    "dateOfBirth": "09022013",
    "middleName": "E",
    "phoneNumber": "8094567777",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  }
]
                """.trimIndent()
    }

}