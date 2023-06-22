package com.pelsoczi.googlebookssibs.data.remote

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader

class NetworkDataSourceTest {

    private val mockWebServer: MockWebServer = MockWebServer()

    private val httpClient = OkHttpClient.Builder().build()

    private val apiService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(BooksApiService::class.java)

    private lateinit var networkDataSource: NetworkDataSource

    @Before
    fun setUp() {
        networkDataSource = NetworkDataSource(
            apiService,
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `200 request executed successfully`() = runBlocking {
        // given
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(readJsonFile("200-success-items.json"))
        mockWebServer.enqueue(response)

        // when
        val result = networkDataSource.fetch().blockingGet()
        // then
        networkDataSource.fetch()
        assertThat(result.isSuccessful).isTrue()
        assertThat(result.code()).isEqualTo(200)
    }

    @Test
    fun `400 request unacceptable, missing or misconfigured parameter`() = runBlocking {
        // given
        val response = MockResponse()
            .setResponseCode(400)
            .setBody(readJsonFile("400-invalid-param.json"))
        mockWebServer.enqueue(response)
        // when
        val result = networkDataSource.fetch().blockingGet()
        // then
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.code()).isEqualTo(400)
    }

    @Test
    fun `403 api key is disabled`() = runBlocking {
        // given
        val response = MockResponse()
            .setResponseCode(403)
            .setBody(readJsonFile("403-disabled-api.json"))
        mockWebServer.enqueue(response)
        // when
        val result = networkDataSource.fetch().blockingGet()
        // then
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.code()).isEqualTo(403)
    }

    @Test
    fun `400 api key is invalid`() = runBlocking {
        // given
        val response = MockResponse()
            .setResponseCode(400)
            .setBody(readJsonFile("400-api-key-invalid.json"))
        mockWebServer.enqueue(response)
        // when
        val result = networkDataSource.fetch().blockingGet()
        // then
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.code()).isEqualTo(400)
    }

    private fun readJsonFile(filename: String) =
        javaClass.classLoader?.getResourceAsStream(filename).use { inputStream ->
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(inputStream))
            var string = reader.readLine()
            while (string != null) {
                builder.append(string)
                string = reader.readLine()
            }
            builder.toString()
        }

}