package com.cs411cmp003.bodywatchfrontend.di

import com.cs411cmp003.bodywatchfrontend.data.ApiService
import com.cs411cmp003.bodywatchfrontend.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {
    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
//        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor { chain ->
//            val origin = chain.request()
//            val requestBuilder =
//                origin.url.newBuilder().build()
//            val request = origin.newBuilder().url(requestBuilder).build()
//            chain.proceed(request)
//        }
//        httpClient.addNetworkInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(Constants.testUrl)
          //  .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}