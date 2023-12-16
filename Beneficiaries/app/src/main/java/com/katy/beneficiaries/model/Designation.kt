package com.katy.beneficiaries.model

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