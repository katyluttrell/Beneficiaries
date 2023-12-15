package com.katy.beneficiaries.util

import android.content.Context
import android.util.Log
import io.mockk.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets

internal class JsonLoaderTest {

    private val testCoroutineScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testCoroutineScheduler)
    private val context = mockk<Context>(relaxed = true)
    private val jsonLoader = JsonLoader()

    @Test
    fun testJsonLoaderSuccess() = runTest(testCoroutineScheduler) {
        val testFileName = "test.json"
        val testString = "{'key1': 'value1', 'key2': 'value2'}"
        every { context.assets.open(testFileName) } returns ByteArrayInputStream(
            testString.toByteArray(
                StandardCharsets.UTF_8
            )
        )

        val result = jsonLoader.loadJson(context, testFileName, testDispatcher)
        assertEquals(testString, result)
    }

    @Test
    fun testJsonLoaderEmptyFile() = runTest(testCoroutineScheduler) {
        val testFileName = "test.json"
        every { context.assets.open(testFileName) } returns ByteArrayInputStream(byteArrayOf())

        val result = jsonLoader.loadJson(context, testFileName, testDispatcher)
        assertEquals("", result)
    }

    @Test
    fun testJsonLoaderException() = runTest(testCoroutineScheduler) {
        mockkStatic(Log::class)
        every { Log.e(ofType(), ofType()) }  returns 0
        val testFileName = "test.json"
        every { context.assets.open(testFileName) } throws FileNotFoundException()

        val result = jsonLoader.loadJson(context, testFileName, testDispatcher)
        assertEquals(null, result)
        verify { Log.e("Json Loader", "Unable to read json file $testFileName") }
    }

}