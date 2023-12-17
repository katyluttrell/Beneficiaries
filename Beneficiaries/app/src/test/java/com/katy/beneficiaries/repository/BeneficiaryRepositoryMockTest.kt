package com.katy.beneficiaries.repository

import android.content.Context
import android.util.Log
import com.katy.beneficiaries.json.BeneficiaryJsonParser
import com.katy.beneficiaries.json.JsonLoader
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.model.Designation
import com.katy.beneficiaries.util.Constants
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.system.measureTimeMillis


internal class BeneficiaryRepositoryMockTest {

    val mockContext = mockk<Context>()
    val mockJsonParser = mockk<BeneficiaryJsonParser>()
    val mockJsonLoader = mockk<JsonLoader>()
    val testCoroutineScheduler = TestCoroutineScheduler()
    val testDispatcher = StandardTestDispatcher(testCoroutineScheduler)
    val respository = BeneficiaryRepository(mockJsonParser, mockJsonLoader, testDispatcher)

    val beneficiary = Beneficiary(
        "Luttrell",
        "Leo",
        Designation.PRIMARY,
        "Child",
        null,
        LocalDate.of(2020, 6, 2),
        "Nunzio",
        null,
        null
    )

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) } returns 0
    }

    @After
    fun cleanup(){
        unmockkAll()
    }

    @Test
    fun testGetBeneficiaryDataSuccess() = runTest(testCoroutineScheduler) {
        setupSuccessfulJsonLoader()
        setupSuccessfulJsonParser()

        val list = respository.getBeneficiaryData(mockContext)?.awaitAll()
        assertEquals(listOf(beneficiary, beneficiary), list)
    }

    @Test
    fun testGetBeneficiaryDataLoadJsonProblem() = runTest(testCoroutineScheduler) {
        setupMalformedJsonLoader()

        val list = respository.getBeneficiaryData(mockContext)?.awaitAll()
        assertEquals(null, list)
    }

    @Test
    fun testGetBeneficiaryDataLoadJsonNull() = runTest(testCoroutineScheduler) {
        coEvery {
            mockJsonLoader.loadJson(
                mockContext,
                Constants.BENEFICIARIES_FILE_NAME
            )
        } returns null

        val list = respository.getBeneficiaryData(mockContext)?.awaitAll()
        assertEquals(null, list)
    }

    @Test
    fun testGetBeneficiaryDataNullArrayProblem() = runTest(testCoroutineScheduler) {
        coEvery {
            mockJsonLoader.loadJson(
                mockContext,
                Constants.BENEFICIARIES_FILE_NAME
            )
        } returns ""

        val list = respository.getBeneficiaryData(mockContext)?.awaitAll()
        assertEquals(null, list)
    }

    @Test
    fun testGetBeneficiaryDataNullJsonObjectProblem() = runTest(testCoroutineScheduler) {
        coEvery {
            mockJsonLoader.loadJson(
                mockContext,
                Constants.BENEFICIARIES_FILE_NAME
            )
        } returns "[{}]"

        val list = respository.getBeneficiaryData(mockContext)?.awaitAll()
        assertEquals(listOf(null), list)
    }

    @Test
    fun testGetBeneficiaryDataNullBeneficiaryProblem() = runTest(testCoroutineScheduler) {
        setupSuccessfulJsonLoader()
        every { mockJsonParser.parseBeneficiary(ofType()) } returns null

        val list = respository.getBeneficiaryData(mockContext)?.awaitAll()
        assertEquals(listOf(null, null), list)
    }

    @Test
    fun testParallelExecution() = runTest(testCoroutineScheduler) {
        setupTenJsonLoader()
        every { mockJsonParser.parseBeneficiary(ofType()) } answers { Thread.sleep(500); beneficiary }

        var list: List<Beneficiary?>? = null
        val time = measureTimeMillis {
            list = BeneficiaryRepository(
                mockJsonParser,
                mockJsonLoader,
                Dispatchers.Default
            ).getBeneficiaryData(mockContext)?.awaitAll()
        }

        assert(time < 6000)
    }

    fun setupTenJsonLoader() {
        coEvery { mockJsonLoader.loadJson(mockContext, Constants.BENEFICIARIES_FILE_NAME) } returns
                """
                    [
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"},
                      {"name": "Leo", "species": "Cat"}
                      
                    ]
                """.trimIndent()
    }

    fun setupSuccessfulJsonLoader() {
        coEvery { mockJsonLoader.loadJson(mockContext, Constants.BENEFICIARIES_FILE_NAME) } returns
                """
                    [
                      {"name": "Katy", "species": "Human"},
                      {"name": "Leo", "species": "Cat"}
                    ]
                """.trimIndent()
    }

    fun setupMalformedJsonLoader() {
        coEvery { mockJsonLoader.loadJson(mockContext, Constants.BENEFICIARIES_FILE_NAME) } returns
                """
                    [
                      {"name": "Katy", "species": "Human"}
                      {"name": "Leo", "species": "Cat"}
                    ]
                """.trimIndent()
    }

    fun setupSuccessfulJsonParser() {
        every { mockJsonParser.parseBeneficiary(ofType()) } returns beneficiary
    }
}