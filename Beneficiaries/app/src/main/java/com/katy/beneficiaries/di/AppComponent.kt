package com.katy.beneficiaries.di

import com.katy.beneficiaries.json.AddressJsonParser
import com.katy.beneficiaries.json.BeneficiaryJsonParser
import com.katy.beneficiaries.json.JsonLoader
import com.katy.beneficiaries.repository.BeneficiaryRepository
import com.katy.beneficiaries.util.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppComponent {

    private lateinit var appModule: AppModule

    fun initialize( appModule: AppModule){
        this.appModule = appModule
    }

    fun getStringUtils(): StringUtils = appModule.provideStringUtils()
    fun getAddressJsonParser(): AddressJsonParser = appModule.provideAddressJsonParser()
    fun getIODispatcher(): CoroutineDispatcher = appModule.provideIODispatcher()
    fun getDefaultDispatcher(): CoroutineDispatcher = appModule.provideDefaultDispatcher()
    fun getJsonLoader(): JsonLoader = appModule.provideJsonLoader(getIODispatcher())
    fun getBeneficiaryJsonParser(): BeneficiaryJsonParser =
        appModule.provideBeneficiaryJsonParser(getStringUtils(), getAddressJsonParser())
    fun getBeneficiaryRepository(): BeneficiaryRepository =
        appModule.provideBeneficiaryRepository(
            getBeneficiaryJsonParser(),
            getJsonLoader(),
            getDefaultDispatcher()
        )
}