package com.project.squareempdirectory.retrofit.model

import com.google.gson.GsonBuilder
import com.project.squareempdirectory.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Provides retrofit client instance to consume http APIs.
 */

@InstallIn(SingletonComponent::class)
@Module
class SquareAPIService {

    @Provides
    fun providesEmployeeService() : SquareEmployeeService {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(AppConstants.BASE_URL)
            .client(prepareOkHttpClient())
            .build()

        return retrofit.create(SquareEmployeeService::class.java)
    }

    private fun prepareOkHttpClient() : OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.connectTimeout(AppConstants.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        builder.readTimeout(AppConstants.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        builder.writeTimeout(AppConstants.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        return builder.build()
    }
}