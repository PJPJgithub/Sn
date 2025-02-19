package com.gm.carcontrolsim.domain.usecase.news

import com.gm.carcontrolsim.domain.entity.Article
import javax.inject.Inject
//다음 뉴스 헤드라인을 보여주는 역할
class ShowNextHeadlineUseCase @Inject constructor() {
    operator fun invoke(articles: List<Article>, currentArticleIndex: Int): Pair<Int, Article?> {
        return if (articles.isNotEmpty() && currentArticleIndex < articles.size - 1) {
            val nextIndex = currentArticleIndex + 1
            nextIndex to articles[nextIndex]
        } else {
            currentArticleIndex to null
        }
    }
}