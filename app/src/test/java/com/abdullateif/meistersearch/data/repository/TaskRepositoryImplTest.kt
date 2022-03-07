package com.abdullateif.meistersearch.data.repository

import com.abdullateif.meistersearch.data.remote.model.FilterType
import com.abdullateif.meistersearch.data.local.LocalDataSource
import com.abdullateif.meistersearch.data.remote.TaskApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import org.junit.Test
import org.mockito.kotlin.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class TaskRepositoryImplTest {
    lateinit var repository: TaskRepositoryImpl
    lateinit var taskApi: TaskApi
    var taskLocalSource: LocalDataSource = mock()
    lateinit var mockServer: MockWebServer

    val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        configureMockServer()
        taskApi = provideTestApi()
        repository = TaskRepositoryImpl(taskApi, taskLocalSource, dispatcher)
    }

    @After
    fun tearDown() {
        stopMockServer()
    }

    @Test
    fun `fetch tasks successfully by given mock data`() = runTest {
        mockHttpResponse("api_response/mock_tasks.json", HttpURLConnection.HTTP_OK)

        val result = repository.searchTasks("ask", FilterType.ALL)
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `fetch characters with exception`() = runTest {
        mockHttpResponse("api_response/mock_tasks.json", HttpURLConnection.HTTP_FORBIDDEN)

        val result = repository.searchTasks("fi", FilterType.ACTIVE)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.data).isNull()
    }

    fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    fun stopMockServer() {
        mockServer.shutdown()
    }

    fun mockHttpResponse(fileName: String, responseCode: Int) =
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    fun getJson(path: String): String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    fun provideTestApi(): TaskApi {
        val okHttpClient = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TaskApi::class.java)
    }
}