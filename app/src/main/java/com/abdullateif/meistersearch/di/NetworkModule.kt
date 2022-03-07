package com.abdullateif.meistersearch.di

import com.abdullateif.meistersearch.domain.repository.TaskRepository
import com.abdullateif.meistersearch.common.Config
import com.abdullateif.meistersearch.data.local.LocalDataSource
import com.abdullateif.meistersearch.data.remote.TaskApi
import com.abdullateif.meistersearch.data.repository.TaskRepositoryImpl
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader(
                        "Authorization",
                        "Bearer ${Config.BEARER_TOKEN}"
                    )
                    .build()
                chain.proceed(newRequest)
            }
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideProductsApi(retrofit: Retrofit): TaskApi {
        return retrofit.create(TaskApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        api: TaskApi,
        localDataSource: LocalDataSource,
        @DispatcherModule.IoDispatcher
        ioDispatcher: CoroutineDispatcher
    ): TaskRepository {
        return TaskRepositoryImpl(api, localDataSource, ioDispatcher)
    }

}