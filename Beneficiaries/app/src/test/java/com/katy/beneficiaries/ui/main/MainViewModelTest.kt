package com.katy.beneficiaries.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.katy.beneficiaries.di.AppComponent
import com.katy.beneficiaries.handler.DataFetchResultHandler
import com.katy.beneficiaries.mockHelper.MockAppModule
import com.katy.beneficiaries.mockHelper.checkNeverSet
import com.katy.beneficiaries.mockHelper.getOrAwaitValue
import com.katy.beneficiaries.model.Address
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.model.Designation
import com.katy.beneficiaries.ui.adapter.CardDataWrapper
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainViewModel
    val coroutineScheduler = TestCoroutineScheduler()
    val testCoroutineDispatcher = UnconfinedTestDispatcher(coroutineScheduler)
    val data1 = Beneficiary(
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

    val data2 = Beneficiary(
        "Jane",
        "Smith",
        Designation.CONTINGENT,
        "Child",
        "XXX-XX-4664",
        LocalDate.of(2012, 1, 11),
        "E",
        "303-445-5555",
        Address(
            "8515 E Orchard Rd", null, "Greenwood Village", "80111", "CO", "US"
        )
    )

    var missingDataCallbackTest = false
    var noDataCallbackTest = false
    val testHandler = object : DataFetchResultHandler{
        override fun missingDataCallback() {
            missingDataCallbackTest = true
        }

        override fun noDataCallback() {
            noDataCallbackTest = true
        }

    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        missingDataCallbackTest = false
        noDataCallbackTest = false
        AppComponent.initialize(MockAppModule())
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel = MainViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanup() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun testInitiateDataFetchSuccess() =runTest(testCoroutineDispatcher){
        val beneficiaryRepository = AppComponent.getBeneficiaryRepository()
        val mockResponse = makeSuccessfulResult()
        coEvery { beneficiaryRepository.getBeneficiaryData(ofType()) } returns mockResponse

        viewModel.initiateDataFetch(mockk(), testHandler)
        val expectedResult = listOf(
            CardDataWrapper(data1),
            CardDataWrapper(data2)
        )

        val result = viewModel.beneficiaryData.getOrAwaitValue()
        assertEquals(expectedResult, result)
        assertFalse(missingDataCallbackTest)
        assertFalse(noDataCallbackTest)
    }

    @Test
    fun testInitiateDataFetchMissingValue() =runTest(testCoroutineDispatcher){
        val beneficiaryRepository = AppComponent.getBeneficiaryRepository()
        val mockResponse = makeMissingDataResult()
        coEvery { beneficiaryRepository.getBeneficiaryData(ofType()) } returns mockResponse

        viewModel.initiateDataFetch(mockk(), testHandler)
        val expectedResult = listOf(
            CardDataWrapper(data1)
        )

        val result = viewModel.beneficiaryData.getOrAwaitValue()
        assertEquals(expectedResult, result)
        assert(missingDataCallbackTest)
        assertFalse(noDataCallbackTest)
    }

    @Test
    fun testInitiateDataFetchNullList() =runTest(testCoroutineDispatcher){
        val beneficiaryRepository = AppComponent.getBeneficiaryRepository()
        coEvery { beneficiaryRepository.getBeneficiaryData(ofType()) } returns null

        viewModel.initiateDataFetch(mockk(), testHandler)

        val result = viewModel.beneficiaryData.checkNeverSet()
        assert(result)
        assertFalse(missingDataCallbackTest)
        assert(noDataCallbackTest)
    }

    @Test
    fun testInitiateDataFetchEmptyList() =runTest(testCoroutineDispatcher){
        val beneficiaryRepository = AppComponent.getBeneficiaryRepository()
        coEvery { beneficiaryRepository.getBeneficiaryData(ofType()) } returns listOf()

        viewModel.initiateDataFetch(mockk(), testHandler)

        val result = viewModel.beneficiaryData.checkNeverSet()
        assert(result)
        assertFalse(missingDataCallbackTest)
        assert(noDataCallbackTest)
    }

    fun makeSuccessfulResult(): List<Deferred<Beneficiary>> = runBlocking {
        val deferred1 = async { data1 }
        val deferred2 = async { data2 }
        listOf(deferred1, deferred2)
    }

    fun makeMissingDataResult(): List<Deferred<Beneficiary?>> = runBlocking {
        val deferred1 = async { data1 }
        val deferred2 = async { null }
        listOf(deferred1, deferred2)
    }
}