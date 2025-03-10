package com.gm.carcontrolsim.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gm.carcontrolsim.R
import com.gm.carcontrolsim.databinding.ActivityMainBinding
import com.gm.carcontrolsim.viewmodel.MainActivityViewModel
import com.gm.carcontrolsim.viewmodel.MainActivityViewModel.Companion.TAG
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import java.util.Locale

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var textToSpeech: TextToSpeech
    private val mainScope = MainScope()

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 전체 화면 모드 활성화 및 cutout 영역 채우기
        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        textToSpeech = TextToSpeech(this, this)

        checkPermission()
        createSpeechListener()
        setOnClickListener()
        observeViewModel()

        setupUI()
    }

    override fun onDestroy() {
        textToSpeech.shutdown()
        super.onDestroy()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnClickListener() {
        binding.voiceButton.setOnTouchListener { _, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    if (textToSpeech.isSpeaking) {
                        textToSpeech.stop()
                    }
                    mainActivityViewModel.continueSpeaking = false
                    createSpeechListener()
                    binding.voiceButton.setImageResource(R.drawable.black_mike_image) // 음성인식 가능 상태
                }
            }
            false
        }
    }

    private val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    }

    private fun createSpeechListener() {
        mainActivityViewModel.createSpeechListener()
    }

    private fun observeViewModel() {//viewmodel과 상호작용
        mainActivityViewModel.speechText.observe(this) { text ->
            binding.voiceView.text = text
            binding.voiceView.visibility = View.VISIBLE
            binding.voiceButton.setImageResource(R.drawable.mike_image) // 기본 이미지로 변경

            // Hide the voice_view after displaying the speech text
            binding.voiceView.postDelayed({
                binding.voiceView.visibility = View.GONE
            }, 1500) // Adjust the delay as needed
        }

        mainActivityViewModel.newsHeadline.observe(this) { headline ->
            binding.newsHeadline.text = headline
            textToSpeech.speak(headline, TextToSpeech.QUEUE_FLUSH, null, null)
            Log.i(TAG, "ttsHeadlineComplete")
        }

        mainActivityViewModel.newsImage.observe(this) { imageUrl ->
            // 이미지 로드 로직 수정 (Picasso 사용)
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.loading_image) // 로딩 이미지
                    .error(R.drawable.symbol_image) // 로딩 실패 시
                    .into(binding.newsImage)
            } else {
                binding.newsImage.setImageResource(R.drawable.symbol_image) // 이미지가 없을 때 기본 이미지 설정
            }
        }

        mainActivityViewModel.newsDescription.observe(this) { description ->
            textToSpeech.speak(description, TextToSpeech.QUEUE_FLUSH, null, null)
            Log.i(TAG, "ttsDescriptionComplete")
        }

        mainActivityViewModel.errordetection.observe(this) { errordetection ->
            textToSpeech.speak(errordetection, TextToSpeech.QUEUE_FLUSH, null, null)
            Log.i(TAG, "ttsErrorComplete")
        }

        mainActivityViewModel.exitApp.observe(this) { shouldExit ->
            if (shouldExit) {
                finishAndRemoveTask()
            }
        }
    }

    private fun checkPermission() {
        val granted = mainActivityViewModel.getRecordPermissionGranted()
        if (!granted) {
            requestPermission()
        } else {
            Log.i(TAG, "Permission Already Granted")
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permission ->
        when (permission) {
            true -> {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            false -> {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.getDefault()
            // 앱 시작 시 "Welcome" 메시지 표시 및 TTS로 말하기
            val welcomeMessage = "Welcome"
            binding.newsHeadline.text = welcomeMessage
            textToSpeech.speak(welcomeMessage, TextToSpeech.QUEUE_FLUSH, null, null)
            binding.newsImage.setImageResource(R.drawable.symbol_image) // 시작 이미지
            binding.voiceButton.setImageResource(R.drawable.black_mike_image) // 음성인식 가능 상태
        }
    }

    private fun setupUI() {//UI
        // 기사 상세 페이지로 이동
        binding.newsView.setOnClickListener {
            mainActivityViewModel.continueSpeaking = false

            if (mainActivityViewModel.articles.isNotEmpty() && (mainActivityViewModel.currentArticleIndex >= 0 && mainActivityViewModel.currentArticleIndex < mainActivityViewModel.articles.size)) {
                val currentArticle = mainActivityViewModel.articles[mainActivityViewModel.currentArticleIndex]
                val intent = Intent(this, ArticleDetailActivity::class.java).apply {
                    putExtra("headline", currentArticle.title)
                    putExtra("query", mainActivityViewModel.currentQuery)
                    putExtra("url", currentArticle.url)
                    putExtra("imageUrl", currentArticle.urlToImage)
                    putExtra("description", currentArticle.description)
                    putExtra("source", currentArticle.source.name)
                    putExtra("author", currentArticle.author)
                    putExtra("publishedAt", currentArticle.publishedAt)
                }
                startActivity(intent)
            } else {
                Log.d(TAG, "No news found!")
            }
        }

        // 이전 기사 버튼 클릭 리스너
        binding.prevButton.setOnClickListener {
            mainActivityViewModel.continueSpeaking = false
            mainActivityViewModel.showPreviousHeadline()
        }

        // 다음 기사 버튼 클릭 리스너
        binding.nextButton.setOnClickListener {
            mainActivityViewModel.continueSpeaking = false
            mainActivityViewModel.showNextHeadline()
        }

        // 목록 버튼 클릭 리스너
        binding.listButton.setOnClickListener {
            mainActivityViewModel.continueSpeaking = false

            val headlines = mainActivityViewModel.searchedArticles.map { it.first }
            val times = mainActivityViewModel.searchedArticles.map { it.second }
            val queries = mainActivityViewModel.previousQueries
            val urls = mainActivityViewModel.viewedArticles.map { it.url }
            val description = mainActivityViewModel.viewedArticles.map { it.description }
            val source = mainActivityViewModel.viewedArticles.map { it.source.name }
            val author = mainActivityViewModel.viewedArticles.map { it.author }
            val publishedAt = mainActivityViewModel.viewedArticles.map { it.publishedAt }
            val urlToImage = mainActivityViewModel.viewedArticles.map { it.urlToImage }

            val intent = Intent(this, ArticleListActivity::class.java).apply {
                putStringArrayListExtra("headlines", ArrayList(headlines))
                putStringArrayListExtra("queries", ArrayList(queries))
                putStringArrayListExtra("urls", ArrayList(urls))
                putStringArrayListExtra("times", ArrayList(times))
                putStringArrayListExtra("descriptions", ArrayList(description))
                putStringArrayListExtra("sources", ArrayList(source))
                putStringArrayListExtra("authors", ArrayList(author))
                putStringArrayListExtra("publishedAts", ArrayList(publishedAt))
                putStringArrayListExtra("urlToImages", ArrayList(urlToImage))
            }
            startActivity(intent)
        }

        // 리셋 버튼 클릭 리스너 추가
        binding.resetButton.setOnClickListener {
            if (textToSpeech.isSpeaking) {
                textToSpeech.stop()
            }
            mainActivityViewModel.resetf()
        }
    }
}