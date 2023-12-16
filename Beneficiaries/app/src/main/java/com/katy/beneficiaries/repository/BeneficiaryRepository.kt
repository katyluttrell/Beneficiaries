package com.katy.beneficiaries.repository

import android.content.Context
import com.katy.beneficiaries.json.BeneficiaryJsonParser
import com.katy.beneficiaries.json.JsonLoader
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.util.Constants
import com.katy.beneficiaries.util.getJSONArrayOrNull
import com.katy.beneficiaries.util.getJSONObjectOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class BeneficiaryRepository(
    private val beneficiaryJsonParser: BeneficiaryJsonParser,
    private val jsonLoader: JsonLoader,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend fun getBeneficiaryData(context: Context): List<Deferred<Beneficiary?>>? {
        return jsonLoader.loadJson(context, Constants.BENEFICIARIES_FILE_NAME)?.let { jsonString ->
            jsonString.getJSONArrayOrNull()?.let { arr ->
                coroutineScope {
                    (0 until arr.length()).map { i ->
                        async(defaultDispatcher) {
                            arr.getJSONObjectOrNull(i)
                                ?.let { beneficiaryJsonParser.parseBeneficiary(it) }
                        }
                    }
                }
            }
        }
    }
}