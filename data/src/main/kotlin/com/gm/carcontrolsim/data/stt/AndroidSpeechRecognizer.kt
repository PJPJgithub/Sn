package com.gm.carcontrolsim.data.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.gm.carcontrolsim.domain.entity.Response
import com.gm.carcontrolsim.domain.entity.SpeechToTextEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Locale
import javax.inject.Inject


class AndroidSpeechRecognizer @Inject constructor(
    @ApplicationContext private val context: Context,
) : SpeechToTextEngine {

    private lateinit var speechRecognizer: SpeechRecognizer

    // TODO: In this example, Language is hard-coded.
    // TODO: This can be changed in start method by specified parameter about locale.
    private val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//         Korean recognizer.
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
    }

    override fun start(): Flow<Response<SpeechToTextEvent>> = callbackFlow {

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        val listener = object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                Log.i(TAG, "onReadyForSpeech")
                trySend(Response.Success(SpeechToTextEvent.ReadyToSpeech()))
            }

            override fun onBeginningOfSpeech() {
                Log.i(TAG, "onBeginningOfSpeech")
                trySend(Response.Success(SpeechToTextEvent.BeginingOfSpeech()))
            }

            override fun onRmsChanged(p0: Float) {
//                Log.i(TAG, "onRmsChanged")
            }

            override fun onBufferReceived(p0: ByteArray?) {
                Log.i(TAG, "onBufferReceived")
            }

            override fun onEndOfSpeech() {
                Log.i(TAG, "onEndOfSpeech")
                trySend(Response.Success(SpeechToTextEvent.EndOfSpeech()))
            }

            override fun onError(p0: Int) {
                val errorMessage = "Error code: $p0"
                Log.i(TAG, "onError : $errorMessage")
                trySend(Response.Success(SpeechToTextEvent.Error(errorMessage)))
            }

            override fun onResults(bundle: Bundle?) {
                val data = bundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Log.i(TAG, "onResults:${data!!.get(0)}")
                trySend(Response.Success(SpeechToTextEvent.SpeechResult(data!!.get(0))))
            }

            override fun onPartialResults(p0: Bundle?) {
                Log.i(TAG, "onPartialResults")
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
                Log.i(TAG, "onEvent")
            }
        }

        Log.i(TAG, "setRecognitionListener")
        speechRecognizer.setRecognitionListener(listener)

        speechRecognizer.startListening(speechRecognizerIntent)

        awaitClose {

            // TODO : make concrete code to stop properly.
            // stop()

            // TODO: This should be isolated to other member function
            speechRecognizer.destroy()
        }
    }

    override fun stop() {
        Log.i(TAG, "stopListening")
        speechRecognizer.stopListening()
    }

//    // This is just a sample to show how to use the proto buf
//    fun updateProto() {
//        val recordingStatus = UpdateRecordingStatus.newBuilder()
//            .setRecordingStatus(RecordingStatus.RECORDING).setVoiceText("abcd")
//            .build()
//
//        val serializedRecordingStatus = recordingStatus.toByteArray()
//
//        //send to anywhere
//
//        val receivedRecordingStatus = UpdateRecordingStatus.parseFrom(serializedRecordingStatus)
//
//    }

    companion object {
        private const val TAG = "AndroidSpeechRecognizer"
    }
}
