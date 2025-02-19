package com.gm.carcontrolsim.data.news

import com.gm.carcontrolsim.domain.entity.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
//Retrofit을 사용하여 뉴스 API를 호출하는 인터페이스
interface NewsApiService {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}