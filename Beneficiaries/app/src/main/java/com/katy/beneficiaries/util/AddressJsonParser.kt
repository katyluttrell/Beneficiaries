package com.katy.beneficiaries.util

import com.katy.beneficiaries.model.Address
import com.katy.beneficiaries.model.AddressKeys
import com.katy.beneficiaries.model.hasAtLeastOneEssentialAttribute
import org.json.JSONObject

internal class AddressJsonParser {

    fun parseAddress(jsonObject: JSONObject): Address? {
        val address = with(jsonObject) {
            Address(
                getStringOrNull(AddressKeys.FIRST_LINE_MAILING),
                getStringOrNull(AddressKeys.SCND_LINE_MAILING),
                getStringOrNull(AddressKeys.CITY),
                getStringOrNull(AddressKeys.ZIP_CODE),
                getStringOrNull(AddressKeys.STATE_CODE),
                getStringOrNull(AddressKeys.COUNTRY)
            )
        }
        return if (address.hasAtLeastOneEssentialAttribute()) {
            address
        } else null
    }
}