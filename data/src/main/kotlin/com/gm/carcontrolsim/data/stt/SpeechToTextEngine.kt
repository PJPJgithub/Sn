package com.gm.carcontrolsim.data.stt

import com.gm.carcontrolsim.domain.entity.Response
import com.gm.carcontrolsim.domain.entity.SpeechToTextEvent
import kotlinx.coroutines.flow.Flow

interface SpeechToTextEngine {
    fun start(): Flow<Response<SpeechToTextEvent>>
    fun stop()
}
