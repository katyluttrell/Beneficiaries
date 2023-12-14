package com.katy.beneficiaries.model

import java.time.LocalDate

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
