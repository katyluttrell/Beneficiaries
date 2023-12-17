package com.katy.beneficiaries.handler

interface DataFetchResultHandler {
    fun missingDataCallback()
    fun noDataCallback()
}