package com.katy.beneficiaries.ui.adapter

data class CardDataWrapper<T>(
    val data: T,
    var isExpanded: Boolean = false
)