package com.katy.beneficiaries.util

object Constants {
    const val OPTIONALLY_CENSORED_SSN_REGEX = "^[0-9X]{5}[0-9]{4}$"
    const val SSN_FORMAT = "XXX-XX-%s"
    const val PHONE_NUMBER_FORMAT = "%s-%s-%s"
    const val PHONE_NUMBER_LENGTH = 10
    const val DATE_FORMAT = "MMddyyyy"
    const val BENEFICIARIES_FILE_NAME = "Beneficiaries.json"
}