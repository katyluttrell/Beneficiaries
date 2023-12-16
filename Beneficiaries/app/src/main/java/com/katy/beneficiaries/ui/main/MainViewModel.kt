package com.katy.beneficiaries.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katy.beneficiaries.di.AppComponent
import com.katy.beneficiaries.model.Beneficiary
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val beneficiaryRepository = AppComponent.getBeneficiaryRepository()

    private val _beneficiaryData: MutableLiveData<List<Beneficiary>> = MutableLiveData()
    val beneficiaryData: LiveData<List<Beneficiary>>
        get() = _beneficiaryData

    fun initiateDataFetch(context: Context, errorCallback: () -> Unit) {
        viewModelScope.launch {
            val list = beneficiaryRepository.getBeneficiaryData(context)?.awaitAll()
            if (list?.contains(null) != false) {
                errorCallback()
            }
            list?.let {
                _beneficiaryData.value = it.filterNotNull()
            }
        }
    }
}