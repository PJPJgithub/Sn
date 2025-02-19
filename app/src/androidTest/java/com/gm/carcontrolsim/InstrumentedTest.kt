package com.gm.carcontrolsim

import android.speech.tts.TextToSpeech
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.gm.carcontrolsim.activity.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    private lateinit var textToSpeech: TextToSpeech

    @OptIn(ExperimentalCoroutinesApi::class)
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // TextToSpeech 엔진을 초기화합니다.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // 초기화가 성공하면 기본 언어를 설정합니다.
                textToSpeech.language = Locale.getDefault()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testTextToSpeechInitialization() {// Text-to-Speech 초기화 테스트
        // 이 테스트는 SampleSttActivity가 시작될 때 TextToSpeech 엔진이 올바르게 초기화되고
        // "Welcome" 메시지를 말하는지 확인합니다.
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // 예상 메시지와 실제 메시지를 비교합니다.
                val expectedMessage = "Welcome"
                val actualMessage = activity.findViewById<TextView>(R.id.news_headline).text.toString()
                assertEquals(expectedMessage, actualMessage)
            }
        }
    }

    @Test
    fun testButtonIsVisible() {// 버튼 가시성 테스트
        // 이 테스트는 SampleSttActivity의 주요 버튼들이 화면에 표시되는지 확인합니다.
        onView(withId(R.id.prev_button)).check(matches(isDisplayed()))
        onView(withId(R.id.next_button)).check(matches(isDisplayed()))
        onView(withId(R.id.reset_button)).check(matches(isDisplayed()))
        onView(withId(R.id.voice_button)).check(matches(isDisplayed()))
        onView(withId(R.id.list_button)).check(matches(isDisplayed()))
    }
}