package com.katy.beneficiaries.util

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class StringUtils {

    fun createDateFromString(dateString: String, formatPattern: String): LocalDate? {
        return try {
            val formatter = DateTimeFormatter.ofPattern(formatPattern)
            LocalDate.parse(dateString, formatter)
        } catch (e: Exception) {
            if (e is java.lang.IllegalArgumentException) {
                Log.e("StringUtils", "Unable to create date formatter from pattern $formatPattern")
            }
            if (e is DateTimeParseException) {
                Log.e("StringUtils", "Unable to parse date $dateString")
            }
            null
        }
    }

    /*
        A SSN is considered valid if it is nine characters long and contains only digits
        or a censoring 'X' in the first 5 digits. To SSNs will be formatted with dashes
        censoring the first 5 digits if they are not censored already to be in the format
        XXX-XX-1234
    */
    fun formatAndValidateSSN(ssnString: String): String? {
        val ssnRegex = Constants.OPTIONALLY_CENSORED_SSN_REGEX.toRegex()
        return if (ssnRegex.matches(ssnString)) {
            String.format(Constants.SSN_FORMAT, ssnString.takeLast(4))
        } else null
    }

    fun formatAndValidatePhoneNumber(phoneNumberString: String): String? {
        return if (phoneNumberString.length == Constants.PHONE_NUMBER_LENGTH && phoneNumberString.all { it.isDigit() }) {
            return String.format(
                Constants.PHONE_NUMBER_FORMAT,
                phoneNumberString.subSequence(0, 3),
                phoneNumberString.subSequence(3, 6),
                phoneNumberString.subSequence(6, 10)
            )
        } else null
    }
}