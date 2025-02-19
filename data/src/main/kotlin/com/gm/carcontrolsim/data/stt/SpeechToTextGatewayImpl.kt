package com.gm.carcontrolsim.data.stt


import com.gm.carcontrolsim.domain.entity.Response
import com.gm.carcontrolsim.domain.entity.SpeechToTextEvent
import com.gm.carcontrolsim.domain.gateway.SpeechToTextGateway
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpeechToTextGatewayImpl @Inject constructor(
    private val speechToTextEngine: SpeechToTextEngine
) : SpeechToTextGateway {
    override fun getSpeechToTextEvent(): Flow<Response<SpeechToTextEvent>> {
        return speechToTextEngine.start()
    }
}
