package com.gm.carcontrolsim.data.news

import com.gm.carcontrolsim.domain.gateway.NewsApiGateway
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
//Dagger Hilt를 사용하여 의존성 주입을 설정하는 모듈. Retrofit 인스턴스와 NewsApiService를 제공
@Module
@InstallIn(SingletonComponent::class)
abstract class NewsApiModule {

    @Binds// NewsApiGateway 인터페이스를 구현한 클래스를 바인딩
    abstract fun bindNewsApiGateway(
        newsApiGatewayImpl: NewsApiGatewayImpl
    ): NewsApiGateway

    companion object {
        @Provides// Retrofit 인스턴스를 제공
        @Singleton
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides// NewsApiService 인스턴스를 제공
        @Singleton
        fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
            return retrofit.create(NewsApiService::class.java)
        }
    }
}