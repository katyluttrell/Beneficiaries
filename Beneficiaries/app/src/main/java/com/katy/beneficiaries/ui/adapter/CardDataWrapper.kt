package com.katy.beneficiaries.ui.adapter


/*
This wrapper is to save the expanded state of
data being displayed in a recycler view
 */
data class CardDataWrapper<T>(
    val data: T,
    var isExpanded: Boolean = false
)