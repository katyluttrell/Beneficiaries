package com.katy.beneficiaries.repository

import com.katy.beneficiaries.model.Beneficiary
import kotlinx.coroutines.flow.Flow

internal interface BeneficiaryRepository {
    suspend fun getBeneficiaryData(): Beneficiary
}