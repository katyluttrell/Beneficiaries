package com.katy.beneficiaries.model

internal data class Address(
    val firstLineMailing: String?,
    val scndLineMailing: String?,
    val city: String?,
    val zipCode: String?,
    val stateCode: String?,
    val country: String?
)

internal object AddressKeys {
    const val FIRST_LINE_MAILING = "firstLineMailing"
    const val SCND_LINE_MAILING = "scndLineMailing"
    const val CITY = "city"
    const val ZIP_CODE = "zipCode"
    const val STATE_CODE = "stateCode"
    const val COUNTRY = "country"
}

/*
 An address needs to have at least one of the following: city, zipCode, stateCode, country.
 Just having first and/or second line mailing isn't useful enough to keep.
 */
internal fun Address.hasAtLeastOneEssentialAttribute(): Boolean =
    listOf(city, zipCode, stateCode, country).any { it != null }
