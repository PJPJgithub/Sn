package com.gm.carcontrolsim.domain.usecase.news

import com.gm.carcontrolsim.domain.entity.Article
import javax.inject.Inject
//이전 뉴스 헤드라인을 보여주는 역할
class ShowPreviousHeadlineUseCase @Inject constructor() {
    operator fun invoke(articles: List<Article>, currentArticleIndex: Int): Pair<Int, Article?> {
        return if (articles.isNotEmpty() && currentArticleIndex > 0) {
            val previousIndex = currentArticleIndex - 1
            previousIndex to articles[previousIndex]
        } else {
            currentArticleIndex to null
        }
    }
}