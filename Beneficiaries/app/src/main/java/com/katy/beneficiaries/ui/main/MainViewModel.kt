package com.katy.beneficiaries.ui.main

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katy.beneficiaries.di.AppComponent
import com.katy.beneficiaries.handler.DataFetchResultHandler
import com.katy.beneficiaries.model.Beneficiary
import com.katy.beneficiaries.ui.adapter.CardDataWrapper
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val beneficiaryRepository = AppComponent.getBeneficiaryRepository()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val _beneficiaryData: MutableLiveData<List<CardDataWrapper<Beneficiary>>> =
        MutableLiveData()
    val beneficiaryData: LiveData<List<CardDataWrapper<Beneficiary>>>
        get() = _beneficiaryData

    /*
        If there are any nulls in the list from the repository that means
        that there was corrupted data somewhere so we will alert the user
        that some data may be missing and display what we have. If there is
        simply no data or all the data is corrupted we will tell the user
        that there is no data.
     */
    fun initiateDataFetch(context: Context, resultHandler: DataFetchResultHandler) {
        viewModelScope.launch {
            val list = beneficiaryRepository.getBeneficiaryData(context)?.awaitAll()
            if (list?.contains(null) == true) {
                resultHandler.missingDataCallback()
            }
            if (list?.isEmpty() != false) {
                resultHandler.noDataCallback()
            } else {
                val data = list.filterNotNull().map { CardDataWrapper(it) }
                _beneficiaryData.value = data
            }
        }
    }
}