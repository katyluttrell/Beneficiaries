package com.katy.beneficiaries.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katy.beneficiaries.di.AppComponent
import com.katy.beneficiaries.model.Beneficiary
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val beneficiaryRepository = AppComponent.getBeneficiaryRepository()

    lateinit var data: List<Beneficiary>

    fun initiateDataFetch(context: Context, errorCallback: () -> Unit, onComplete: (List<Beneficiary>) -> Unit) {
        viewModelScope.launch {
            val list = beneficiaryRepository.getBeneficiaryData(context)?.awaitAll()
            if (list?.contains(null) != false) {
                errorCallback()
            }
            list?.let {
                data = it.filterNotNull()
            }
            onComplete(data)
        }
    }
}