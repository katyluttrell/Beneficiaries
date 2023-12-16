package com.katy.beneficiaries.model

import android.content.Context
import com.katy.beneficiaries.R

enum class Designation {
    PRIMARY,
    CONTINGENT
}

fun String.toDesignation(): Designation? {
    return when (this) {
        "P" -> Designation.PRIMARY
        "C" -> Designation.CONTINGENT
        else -> null
    }
}

fun Designation.toLocalizedString(context: Context):String{
    return when(this){
        Designation.PRIMARY -> context.getString(R.string.primary)
        Designation.CONTINGENT -> context.getString(R.string.contingent)
    }
}