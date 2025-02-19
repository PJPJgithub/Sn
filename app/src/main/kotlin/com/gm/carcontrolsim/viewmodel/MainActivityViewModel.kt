package com.gm.carcontrolsim.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gm.carcontrolsim.domain.entity.Article
import com.gm.carcontrolsim.domain.entity.PermissionStatus
import com.gm.carcontrolsim.domain.entity.Response
import com.gm.carcontrolsim.domain.entity.SpeechToTextEvent
import com.gm.carcontrolsim.domain.usecase.news.FetchNewsHeadlineUseCase
import com.gm.carcontrolsim.domain.usecase.news.ShowNextHeadlineUseCase
import com.gm.carcontrolsim.domain.usecase.news.ShowPreviousHeadlineUseCase
import com.gm.carcontrolsim.domain.usecase.permission.GetRecordPermissionUseCase
import com.gm.carcontrolsim.domain.usecase.stt.GetSpeechToTextEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class MainActivityViewModel @Inject constructor(
    private val getRecordPermissionUseCase: GetRecordPermissionUseCase,
    private val getSpeechListenerUseCase: GetSpeechToTextEventUseCase,
    private val fetchNewsHeadlineUseCase: FetchNewsHeadlineUseCase, // Usecase 주입
    private val showNextHeadlineUseCase: ShowNextHeadlineUseCase, // Usecase 주입
    private val showPreviousHeadlineUseCase: ShowPreviousHeadlineUseCase // Usecase 주입
) : ViewModel() {

    private val _speechText = MutableLiveData<String>()
    val speechText: LiveData<String> get() = _speechText

    private val _newsHeadline = MutableLiveData<String>()
    val newsHeadline: LiveData<String> get() = _newsHeadline

    private val _newsImage = MutableLiveData<String>()
    val newsImage: LiveData<String> get() = _newsImage

    private val _newsDescription = MutableLiveData<String>()
    val newsDescription: LiveData<String> get() = _newsDescription

    private val _errordetection = MutableLiveData<String>()
    val errordetection: LiveData<String> get() = _errordetection

    private val _exitApp = MutableLiveData<Boolean>()//앱 종료
    val exitApp: LiveData<Boolean> get() = _exitApp

    var currentArticleIndex = 0
    var currentQuery: String? = null
        set(value) {
            field = value
            value?.let { previousQueries.add(it) }
        }
    val previousQueries = mutableListOf<String>()

    var articles: List<Article> = emptyList()

    // 검색한 기사의 헤드라인과 시간을 저장할 리스트 추가 for 목록
    val searchedArticles = mutableListOf<Pair<String, String>>()

    // 봤던 기사의 정보를 저장할 리스트 추가
    val viewedArticles = mutableListOf<Article>()

    // 플래그 변수 추가 '계속 말해 줘' 제어
    var continueSpeaking = false

    fun getRecordPermissionGranted(): Boolean {
        return when (val response = getRecordPermissionUseCase()) {
            is Response.Success -> {
                response.value == PermissionStatus.GRANTED
            }
            is Response.Error -> return false
        }
    }

    fun createSpeechListener() {
        viewModelScope.launch {
            getSpeechListenerUseCase().collect() { event ->
                when (event) {
                    is SpeechToTextEvent.ReadyToSpeech -> {
                        Log.i(TAG, "ReadyToSpeech")
                    }

                    is SpeechToTextEvent.BeginingOfSpeech -> {
                        Log.i(TAG, "BeginingOfSpeech")
                    }

                    is SpeechToTextEvent.EndOfSpeech -> {
                        Log.i(TAG, "EndOfSpeech")
                    }

                    is SpeechToTextEvent.SpeechResult -> {
                        Log.i(TAG, "Text : ${event.text}")
                        _speechText.value = event.text
                        handleSpeechResult(event.text)
                    }

                    is SpeechToTextEvent.Error -> {
                        if (event.message.contains("7")){
                            _speechText.value = "다시 말해 주세요."
                        } else if (event.message.contains("2")){
                            _speechText.value = "네트워크 연결을 확인해 주세요."
                        } else {
                            _speechText.value = "잠시 후 다시 시도해 주세요."
                        }
                    }
                }
            }
        }
    }

    private suspend fun handleSpeechResult(text: String) {
        val keywordRegex = Regex("(.+) 뉴스 알려 줘")
        val matchResult = keywordRegex.find(text)
        val keyword = matchResult?.groupValues?.get(1)?.trim()

        if (keyword != null) {
            continueSpeaking = false
            fetchNewsHeadline(keyword)
        } else if (text.contains("다음")) {
            continueSpeaking = false
            showNextHeadline()
        } else if (text.contains("이전")) {
            continueSpeaking = false
            showPreviousHeadline()
        } else if (text.contains("내용")) {
            continueSpeaking = false
            showNewsDescription()
        } else if (text.contains("초기화")) { // 추가
            resetf() // 추가
        } else if (text.contains("계속")) { // 추가

            continueSpeaking = true

            while (currentArticleIndex < articles.size && continueSpeaking) {
                showNextHeadline()
                delay(10000)
            }

        } else if (text.contains("종료")) {
            _exitApp.value = true
        } else {
            _errordetection.value="다시 말해 주세요"
        }
    }

    fun fetchNewsHeadline(query: String) {
        viewModelScope.launch {
            fetchNewsHeadlineUseCase(query, "75662f720c4c4b5d80a25b1776843a8b").collect { response ->
                if (response.articles.isNotEmpty()) {
                    articles = response.articles
                    currentQuery = query

                    currentArticleIndex = 0

                    _newsHeadline.value = articles[currentArticleIndex].title
                    _newsImage.value = articles[currentArticleIndex].urlToImage
                    Log.i(TAG, "News fetched successfully: ${articles[currentArticleIndex].title}")

                    // 검색한 기사의 헤드라인이 이미 리스트에 있는지 확인
                    val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                    val isAlreadySearched = searchedArticles.any { it.first == articles[currentArticleIndex].title }

                    if (!isAlreadySearched) {
                        searchedArticles.add(Pair(articles[currentArticleIndex].title, currentTime))
                    }

                    // 봤던 기사의 정보를 리스트에 추가
                    val isAlreadyViewed = viewedArticles.any { it.title == articles[currentArticleIndex].title }
                    if (!isAlreadyViewed) {
                        viewedArticles.add(articles[currentArticleIndex])
                    }

                } else {
                    _newsHeadline.value = "No news found for $query"
                    Log.i(TAG, "No news found for $query")
                }
            }
        }
    }

    fun showNextHeadline() {
        val (nextIndex, nextArticle) = showNextHeadlineUseCase(articles, currentArticleIndex)
        if (nextArticle != null) {
            currentArticleIndex = nextIndex
            _newsHeadline.value = nextArticle.title
            _newsImage.value = nextArticle.urlToImage
            Log.i(TAG, "Next news headline: ${nextArticle.title}")

            // 검색한 기사의 헤드라인이 이미 리스트에 있는지 확인
            val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val isAlreadySearched = searchedArticles.any { it.first == nextArticle.title }

            if (!isAlreadySearched) {
                searchedArticles.add(Pair(nextArticle.title, currentTime))
                currentQuery = currentQuery
            }

            // 봤던 기사의 정보를 리스트에 추가
            val isAlreadyViewed = viewedArticles.any { it.title == nextArticle.title }
            if (!isAlreadyViewed) {
                viewedArticles.add(nextArticle)
            }

        } else {
            _newsImage.value = "" // 마지막 기사 노출 후 다음 오류 시 기사 사진도 삭제
            _newsHeadline.value = "No more news articles"
            currentArticleIndex = articles.size
            Log.i(TAG, "No more news articles")
        }
    }

    fun showPreviousHeadline() {
        val (previousIndex, previousArticle) = showPreviousHeadlineUseCase(articles, currentArticleIndex)
        if (previousArticle != null) {
            currentArticleIndex = previousIndex
            _newsHeadline.value = previousArticle.title
            _newsImage.value = previousArticle.urlToImage
            Log.i(TAG, "Previous news headline: ${previousArticle.title}")

            // 검색한 기사의 헤드라인이 이미 리스트에 있는지 확인
            val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val isAlreadySearched = searchedArticles.any { it.first == previousArticle.title }

            if (!isAlreadySearched) {
                searchedArticles.add(Pair(previousArticle.title, currentTime))
                currentQuery = currentQuery
            }

            // 봤던 기사의 정보를 리스트에 추가
            val isAlreadyViewed = viewedArticles.any { it.title == previousArticle.title }
            if (!isAlreadyViewed) {
                viewedArticles.add(previousArticle)
            }

        } else {
            _newsImage.value = "" // 첫 기사 노출 후 이전 오류 시 기사 사진도 삭제
            _newsHeadline.value = "No previous news articles"
            currentArticleIndex = -1 // 여러번 실행 오류 해결
            Log.i(TAG, "No previous news articles")
        }
    }

    private fun showNewsDescription() {
        if (articles.isNotEmpty()) {
            val description = articles[currentArticleIndex].description + "... 더 자세한 내용은 기사를 참고해 주세요."
            _newsDescription.value = description
            Log.i(TAG, "News description: $description")
        }
    }

    fun resetf() {
        _newsHeadline.value = ""
        _newsImage.value = ""
        articles = emptyList()
        currentArticleIndex = 0
        searchedArticles.clear() // 검색한 기사 목록도 초기화
        viewedArticles.clear() // 본 기사 저장도 초기화
        previousQueries.clear() // previousQueries 초기화
        _newsHeadline.value = "Reset"
        continueSpeaking = false
    }

    fun stopSpeechListener() {
        // TODO: Not yet implemented
    }

    companion object {
        const val TAG = "MainActivityViewModel"
    }
}