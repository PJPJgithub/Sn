package com.gm.carcontrolsim.domain.usecase.news

import com.gm.carcontrolsim.domain.entity.NewsResponse
import com.gm.carcontrolsim.domain.gateway.NewsApiGateway
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
//뉴스 헤드라인을 가져오는 역할
class FetchNewsHeadlineUseCase @Inject constructor(
    private val newsApiGateway: NewsApiGateway
) {
    operator fun invoke(query: String, apiKey: String): Flow<NewsResponse> {
        return newsApiGateway.getNews(query, apiKey)
    }
}