package com.gm.carcontrolsim.data.stt

import com.gm.carcontrolsim.domain.gateway.SpeechToTextGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface SpeechToTextModule {
    @Binds
    @Singleton
    fun bindSpeechToTextGateway(impl: SpeechToTextGatewayImpl): SpeechToTextGateway

    @Binds
    @Singleton
    fun bindSpeechToTextEngine(impl: AndroidSpeechRecognizer): SpeechToTextEngine
}
