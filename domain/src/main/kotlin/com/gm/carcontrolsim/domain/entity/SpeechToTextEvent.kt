package com.gm.carcontrolsim.domain.entity

 sealed class SpeechToTextEvent {
     class ReadyToSpeech() : SpeechToTextEvent()

     class BeginingOfSpeech() : SpeechToTextEvent()

     class EndOfSpeech() : SpeechToTextEvent()

     data class SpeechResult(val text: String) : SpeechToTextEvent()

     data class Error(val message: String) : SpeechToTextEvent() // 에러 이벤트 추가
 }
