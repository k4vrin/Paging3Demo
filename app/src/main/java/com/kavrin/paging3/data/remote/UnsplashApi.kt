package com.kavrin.paging3.data.remote

import com.kavrin.paging3.BuildConfig
import com.kavrin.paging3.model.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {


	@Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
	@GET(value = "/photos")
	suspend fun getAllImages(
		@Query(value = "page") page: Int,
		@Query(value = "per_page") perPage: Int
	): List<UnsplashImage>

	@Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
	@GET(value = "/search/photos")
	suspend fun searchImages(
		@Query(value = "page") page: Int,
		@Query(value = "per_page") perPage: Int
	): List<UnsplashImage>


}