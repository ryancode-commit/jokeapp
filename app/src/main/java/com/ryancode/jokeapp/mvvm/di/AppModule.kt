package com.ryancode.jokeapp.mvvm.di

import com.ryancode.jokeapp.BuildConfig
import com.ryancode.jokeapp.mvvm.data.remote.ApiService
import com.ryancode.jokeapp.mvvm.utils.NetworkConstants.CONNECT_TIMEOUT
import com.ryancode.jokeapp.mvvm.utils.NetworkConstants.READ_TIMEOUT
import com.ryancode.jokeapp.mvvm.utils.NetworkConstants.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL ="https://v2.jokeapi.dev/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
            )
            .build()
    }
    @Provides
    fun provideUserService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}