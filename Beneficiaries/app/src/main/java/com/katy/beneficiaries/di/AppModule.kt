package com.katy.beneficiaries.di

import com.katy.beneficiaries.json.AddressJsonParser
import com.katy.beneficiaries.json.BeneficiaryJsonParser
import com.katy.beneficiaries.json.JsonLoader
import com.katy.beneficiaries.repository.BeneficiaryRepository
import com.katy.beneficiaries.util.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class AppModule {
    open fun provideStringUtils():StringUtils = StringUtils()
    open fun provideAddressJsonParser():AddressJsonParser = AddressJsonParser()
    open fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
    open fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    open fun provideJsonLoader(ioDispatcher: CoroutineDispatcher): JsonLoader = JsonLoader(ioDispatcher)
    open fun provideBeneficiaryJsonParser(stringUtils: StringUtils, addressJsonParser: AddressJsonParser): BeneficiaryJsonParser =
        BeneficiaryJsonParser(stringUtils, addressJsonParser)
    open fun provideBeneficiaryRepository(
        beneficiaryJsonParser: BeneficiaryJsonParser,
        jsonLoader: JsonLoader,
        defaultDispatcher: CoroutineDispatcher): BeneficiaryRepository =
        BeneficiaryRepository(beneficiaryJsonParser, jsonLoader, defaultDispatcher)
}