package com.kavrin.paging3.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kavrin.paging3.data.local.UnsplashDatabase
import com.kavrin.paging3.data.paging.UnsplashRemoteMediator
import com.kavrin.paging3.data.remote.UnsplashApi
import com.kavrin.paging3.model.UnsplashImage
import com.kavrin.paging3.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
	private val unsplashApi: UnsplashApi,
	private val unsplashDatabase: UnsplashDatabase
) {

	fun getAllImages(): Flow<PagingData<UnsplashImage>> {
		val pagingSourceFactory = { unsplashDatabase.unsplashImageDao().getAllImages() }
		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = UnsplashRemoteMediator(
				unsplashApi = unsplashApi,
				unsplashDatabase = unsplashDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}
}