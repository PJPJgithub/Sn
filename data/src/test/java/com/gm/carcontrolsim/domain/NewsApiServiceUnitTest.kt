package com.gm.carcontrolsim.domain

import com.gm.carcontrolsim.data.news.NewsApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//실제 서버와의 통신 없이 API 호출이 예상대로 작동하는지 확인

class NewsApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: NewsApiService

    @Before
    fun setUp() {
        //MockWebServer 시작 가짜 서버 응답을 설정
        mockWebServer = MockWebServer()
        mockWebServer.start()

        //Retrofit 인스턴스 생성 API 호출을 모방
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @After
    fun tearDown() {
        // MockWebServer 종료
        mockWebServer.shutdown()
    }

    @Test
    fun `test getNews returns expected data`() = runBlocking {
        // Given : MockWebServer에 가짜 응답 설정
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "status": "ok",
                    "totalResults": 2,
                    "articles": [
                        {
                            "source": {"id": "1", "name": "Source1"},
                            "author": "Author1",
                            "title": "Title1",
                            "description": "Description1",
                            "url": "http://example.com/1",
                            "urlToImage": "http://example.com/image1",
                            "publishedAt": "2025-02-04T00:00:00Z"
                        },
                        {
                            "source": {"id": "2", "name": "Source2"},
                            "author": "Author2",
                            "title": "Title2",
                            "description": "Description2",
                            "url": "http://example.com/2",
                            "urlToImage": "http://example.com/image2",
                            "publishedAt": "2025-02-04T00:00:00Z"
                        }
                    ]
                }
            """.trimIndent())
        mockWebServer.enqueue(mockResponse)

        // When : API 호출 (실제 API 호출은 X)
        val response = apiService.getNews("keyword", "apiKey")

        // Then : 응답 데이터 검증
        assertEquals("ok", response.status)
        assertEquals(2, response.totalResults)
        assertEquals("Title1", response.articles[0].title)
        assertEquals("Title2", response.articles[1].title)
    }
}