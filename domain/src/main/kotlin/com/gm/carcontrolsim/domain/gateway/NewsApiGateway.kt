package com.gm.carcontrolsim.domain.gateway

import com.gm.carcontrolsim.domain.entity.NewsResponse
import kotlinx.coroutines.flow.Flow
//뉴스 데이터를 가져오는 메서드를 정의하는 인터페이스
interface NewsApiGateway {
    fun getNews(query: String, apiKey: String): Flow<NewsResponse>
}