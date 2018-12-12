package xyz.rakalabs.englishfootball.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.rakalabs.englishfootball.BuildConfig
import xyz.rakalabs.englishfootball.data.model.TeamResponse
import xyz.rakalabs.englishfootball.data.remote.FootballApiService

@RunWith(JUnit4::class)
class FootballApiServiceTest {
    lateinit var mockServer: MockWebServer
    lateinit var apiService: FootballApiService

    @Before
    fun setUpService() {
        mockServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(FootballApiService::class.java)
    }

    @After
    fun shutdownServer() {
        mockServer.shutdown()
    }

    @Test
    fun getDetailTeam() {

        enqueueResponse("detailteam.json")

        var response: TeamResponse = TeamResponse(emptyList())

        runBlocking {
            response = apiService.getDetailTeam("133616").await().body()!!
        }

        assertEquals(response.teams.first().idTeam, "133616")
    }

    private fun enqueueResponse(fileName: String) {

        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)

        val source = Okio.buffer(Okio.source(inputStream))

        mockServer.enqueue(MockResponse().setBody(source.readString(Charsets.UTF_8)))
    }
}