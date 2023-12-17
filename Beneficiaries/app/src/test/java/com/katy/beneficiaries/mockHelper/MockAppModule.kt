package com.katy.beneficiaries.mockHelper

import com.katy.beneficiaries.di.AppModule
import com.katy.beneficiaries.json.BeneficiaryJsonParser
import com.katy.beneficiaries.json.JsonLoader
import com.katy.beneficiaries.repository.BeneficiaryRepository
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher

class MockAppModule: AppModule() {
    lateinit var mockBeneficiaryRepository: BeneficiaryRepository

    override fun provideBeneficiaryRepository(
        beneficiaryJsonParser: BeneficiaryJsonParser,
        jsonLoader: JsonLoader,
        defaultDispatcher: CoroutineDispatcher
    ): BeneficiaryRepository {
        if(!::mockBeneficiaryRepository.isInitialized){
            mockBeneficiaryRepository = mockk()
        }
        return mockBeneficiaryRepository
    }
}