package com.katy.beneficiaries.util

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun JSONObject.getStringOrNull(key:String): String?{
    return try {
        val stringFromJson = this.getString(key)
        if(stringFromJson.isNullOrEmpty()) null else stringFromJson
    } catch (e: JSONException){
        null
    }
}

fun JSONObject.getNestedObjectOrNull(key: String): JSONObject?{
    return try {
        val objectFromJson = this.getJSONObject(key)
        if(objectFromJson.length() < 1) null else objectFromJson
    } catch (e: JSONException){
        null
    }
}

fun String.getJSONArrayOrNull(): JSONArray?{
    return try {
        val arrayFromJson = JSONArray(this)
        if(arrayFromJson.length() < 1) null else arrayFromJson
    }catch (e:JSONException){
        Log.e("JsonHelpers", "Failed to create JSON array. $e")
        null
    }
}

fun JSONArray.getJSONObjectOrNull(i: Int): JSONObject?{
    return try {
        val objectFromArray = this.getJSONObject(i)
        if(objectFromArray.length() < 1) null else objectFromArray
    }catch (e:JSONException){
        Log.e("JsonHelper", "Failed to JSON object from array. $e")
        null
    }
}





