package com.katy.beneficiaries.util

import org.json.JSONException
import org.json.JSONObject

fun JSONObject.getStringOrNull(key:String): String?{
    return try {
        this.getString(key)
    } catch (e: JSONException){
        null
    }
}

fun JSONObject.getNestedObjectOrNull(key: String): JSONObject?{
    return try {
        this.getJSONObject(key)
    } catch (e: JSONException){
        null
    }
}

