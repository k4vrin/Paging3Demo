package com.kavrin.paging3.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kavrin.paging3.data.remote.UnsplashApi
import com.kavrin.paging3.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


	@Provides
	@Singleton
	fun provideHttpClient(): OkHttpClient {
		return OkHttpClient.Builder()
			.connectTimeout(15, TimeUnit.SECONDS)
			.readTimeout(15, TimeUnit.SECONDS)
			.build()
	}


	@Provides
	@Singleton
	fun provideRetrofit(
		okHttpClient: OkHttpClient,
	): Retrofit {
		val contentType = MediaType.get("application/json")
		// To ignore the JSON properties that we don't want
		val json = Json {
			ignoreUnknownKeys = true
		}

		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(json.asConverterFactory(contentType))
			.build()
	}

	@Provides
	@Singleton
	fun provideUnsplashApi(
		retrofit: Retrofit,
	): UnsplashApi {
		return retrofit.create(UnsplashApi::class.java)
	}

}