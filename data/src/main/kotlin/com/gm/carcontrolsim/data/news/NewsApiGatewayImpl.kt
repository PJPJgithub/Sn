package com.gm.carcontrolsim.data.news

import com.gm.carcontrolsim.domain.entity.NewsResponse
import com.gm.carcontrolsim.domain.gateway.NewsApiGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
//NewsApiGateway 인터페이스를 구현한 클래스. 실제로 NewsApiService를 사용하여 뉴스 데이터를 가져옴
class NewsApiGatewayImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsApiGateway {
    override fun getNews(query: String, apiKey: String): Flow<NewsResponse> = flow {// 코루틴 플로우를 사용하여 뉴스 데이터를 가져오는 메서드
        val response = newsApiService.getNews(query, apiKey)// 뉴스 API 호출
        emit(response)// 결과를 플로우로 방출
    }
}