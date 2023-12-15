package com.katy.beneficiaries.model

import java.time.LocalDate

/*
 lastName and firstName are non-nullable as their values are essential for a Beneficiary object
 */
internal data class Beneficiary(
    val lastName: String,
    val firstName: String,
    val designation: Designation?,
    val beneType: String?,
    val ssn: String?,
    val dateOfBirth: LocalDate?,
    val middleName: String?,
    val phoneNumber: String?,
    val address: Address?
)

internal object BeneficiaryKeys {
    const val LAST_NAME = "lastName"
    const val FIRST_NAME = "firstName"
    const val DESIGNATION = "designation"
    const val BENE_TYPE = "beneType"
    const val SSN = "ssn"
    const val DATE_OF_BIRTH = "dateOfBirth"
    const val MIDDLE_NAME = "middleName"
    const val PHONE_NUMBER = "phoneNumber"
    const val ADDRESS = "beneficiaryAddress"
}