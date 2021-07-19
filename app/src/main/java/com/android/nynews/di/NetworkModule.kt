package com.android.nynews.di


import com.android.nynews.network.api.HttpInterceptor
import com.android.nynews.network.api.NewsListService
import com.android.nynews.network.response.FlowCallAdapterFactory
import com.android.nynews.util.BASE_URL
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Module that provides Network related functions
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .callFactory { client.get().newCall(it) }
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsListApi(retrofit: Retrofit): NewsListService {
        return retrofit.create(NewsListService::class.java)
    }

}
