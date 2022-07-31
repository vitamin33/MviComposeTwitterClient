package com.serbyn.mvicomposetwitterclient.di

import com.serbyn.mvicomposetwitterclient.BuildConfig
import com.serbyn.mvicomposetwitterclient.data.remote.MockTwitterApiServiceImpl
import com.serbyn.mvicomposetwitterclient.data.remote.TwitterApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

//    @Provides
//    fun provideTwitterApiService(retrofit: Retrofit): TwitterApiService = TwitterApiService(retrofit)

    @Provides
    fun provideTwitterApiService(retrofit: Retrofit): TwitterApiService = MockTwitterApiServiceImpl()

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        moshi: Moshi,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            )
            .build()
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://some.base.url.example/"
}