package com.gm.carcontrolsim.domain.gateway

import com.gm.carcontrolsim.domain.entity.Response
import com.gm.carcontrolsim.domain.entity.SpeechToTextEvent
import kotlinx.coroutines.flow.Flow

interface SpeechToTextGateway {
    fun getSpeechToTextEvent(): Flow<Response<SpeechToTextEvent>>
}
