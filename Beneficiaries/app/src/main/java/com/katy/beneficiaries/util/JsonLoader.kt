package com.katy.beneficiaries.util

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset

internal class JsonLoader {
    suspend fun loadJson(
        context: Context,
        fileName: String,
        ioDispatcher: CoroutineDispatcher
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