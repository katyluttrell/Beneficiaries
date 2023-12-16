package com.katy.beneficiaries.json

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset

internal class JsonLoader(val ioDispatcher: CoroutineDispatcher) {
    suspend fun loadJson(
        context: Context,
        fileName: String
    ): String? {
        return withContext(ioDispatcher) {
            try {
                context.assets.open(fileName).use { inputStream ->
                    BufferedReader(
                        InputStreamReader(
                            inputStream,
                            Charset.defaultCharset()
                        )
                    ).useLines { lines ->
                        lines.joinToString(separator = "")
                    }
                }
            } catch (e: Exception) {
                Log.e("Json Loader", "Unable to read json file $fileName")
                null
            }
        }
    }
}