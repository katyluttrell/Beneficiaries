package com.katy.beneficiaries.util

import android.util.Log
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.model.BeneficiaryKeys
import com.katy.beneficiaries.model.toDesignation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

internal class BeneficiaryJsonParser {

    val stringUtils = StringUtils()
    val addressJsonParser = AddressJsonParser()
    fun parseBeneficiariesFromJson(jsonString: String, defaultDispatcher: CoroutineDispatcher) =
        flow {
                getJSONArrayOrNull(jsonString)?.let {
                    coroutineScope {
                        for (i in 0 until it.length()) {
                            launch(defaultDispatcher) {
                                parseBeneficiary(it.getJSONObject(i))?.let { emit(it) }
                            }
                        }
                    }
                }
        }

    private fun parseBeneficiary(jsonObject: JSONObject): Beneficiary? {
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
                                "MMddyyyy"
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

    internal fun getJSONArrayOrNull(jsonString: String):JSONArray?{
        return try {
            JSONArray(jsonString)
        }catch (e:JSONException){
            Log.e("BeneficiaryJsonParser", "Failed to create JSON array. $e")
            null
        }
    }
}

