package com.katy.beneficiaries.json

import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.model.BeneficiaryKeys
import com.katy.beneficiaries.model.toDesignation
import com.katy.beneficiaries.util.Constants
import com.katy.beneficiaries.util.StringUtils
import com.katy.beneficiaries.util.getNestedObjectOrNull
import com.katy.beneficiaries.util.getStringOrNull
import org.json.JSONObject

internal class BeneficiaryJsonParser(
    private val stringUtils: StringUtils,
    private val addressJsonParser: AddressJsonParser
) {

    internal fun parseBeneficiary(jsonObject: JSONObject): Beneficiary? {
        return with(jsonObject) {
            getStringOrNull(BeneficiaryKeys.LAST_NAME)?.let { lastName ->
                getStringOrNull(BeneficiaryKeys.FIRST_NAME)?.let { firstName ->
                    Beneficiary(
                        lastName,
                        firstName,
                        getStringOrNull(BeneficiaryKeys.DESIGNATION)?.toDesignation(),
                        getStringOrNull(BeneficiaryKeys.BENE_TYPE),
                        getStringOrNull(BeneficiaryKeys.SSN)?.let {
                            stringUtils.formatAndValidateSSN(
                                it
                            )
                        },
                        getStringOrNull(BeneficiaryKeys.DATE_OF_BIRTH)?.let {
                            stringUtils.createDateFromString(
                                it,
                                Constants.DATE_FORMAT
                            )
                        },
                        getStringOrNull(BeneficiaryKeys.MIDDLE_NAME),
                        getStringOrNull(BeneficiaryKeys.PHONE_NUMBER)?.let {
                            stringUtils.formatAndValidatePhoneNumber(
                                it
                            )
                        },
                        getNestedObjectOrNull(BeneficiaryKeys.ADDRESS)?.let {
                            addressJsonParser.parseAddress(
                                it
                            )
                        }
                    )
                }
            }
        }
    }
}

