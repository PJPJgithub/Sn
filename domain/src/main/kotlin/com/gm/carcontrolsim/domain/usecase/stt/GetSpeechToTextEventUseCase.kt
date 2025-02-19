package com.gm.carcontrolsim.domain.usecase.stt

import com.gm.carcontrolsim.domain.entity.Response
import com.gm.carcontrolsim.domain.entity.SpeechToTextEvent
import com.gm.carcontrolsim.domain.gateway.SpeechToTextGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSpeechToTextEventUseCase @Inject constructor(
    private val speechToTextGateway: SpeechToTextGateway,
) {
    operator fun invoke(): Flow<SpeechToTextEvent> {
        return speechToTextGateway.getSpeechToTextEvent()
            .filter { it is Response.Success }
            .map { (it as Response.Success).value }
    }
}
