package com.kavrin.paging3.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kavrin.paging3.data.local.UnsplashDatabase
import com.kavrin.paging3.data.remote.UnsplashApi
import com.kavrin.paging3.model.UnsplashImage
import com.kavrin.paging3.model.UnsplashRemoteKeys
import com.kavrin.paging3.util.Constants.ITEMS_PER_PAGE

@ExperimentalPagingApi
class UnsplashRemoteMediator(
	private val unsplashApi: UnsplashApi,
	private val unsplashDatabase: UnsplashDatabase,
) : RemoteMediator<Int, UnsplashImage>() {

	private val unsplashImageDao = unsplashDatabase.unsplashImageDao()
	private val unsplashRemoteKeysDao = unsplashDatabase.unsplashRemoteKeysDao()

	override suspend fun load(
		loadType: LoadType,
		state: PagingState<Int, UnsplashImage>,
	): MediatorResult {
		return try {
			//// Current Page ////
			// In order to calculate the nextPage and prevPage we need currentPage
			val currentPage = when (loadType) {
				// Will be triggered the first time we make request to our server
				LoadType.REFRESH -> {
					val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
					remoteKeys?.nextPage?.minus(1) ?: 1
				}
				LoadType.PREPEND -> {
					val remoteKeys = getRemoteKeysFirstItem(state)
					val prevPage = remoteKeys?.prevPage
						?: return MediatorResult.Success(
							endOfPaginationReached = remoteKeys != null
						)
					prevPage
				}
				LoadType.APPEND -> {
					val remoteKeys = getRemoteKeyForLastItem(state)
					val nextPage = remoteKeys?.nextPage
						?: return MediatorResult.Success(
							endOfPaginationReached = remoteKeys != null
						)
					nextPage
				}
			}

			//// List of UnsplashImage ////
			val response = unsplashApi.getAllImages(page = currentPage, perPage = ITEMS_PER_PAGE)
			val endOfPaginatedReached = response.isEmpty()

			val prevPage = if (currentPage == 1) null else currentPage - 1
			val nextPage = if (endOfPaginatedReached) null else currentPage + 1

			// Execute multiple queries in parallel
			unsplashDatabase.withTransaction {
				if (loadType == LoadType.REFRESH) {
					unsplashImageDao.deleteAllImages()
					unsplashRemoteKeysDao.deleteAllRemoteKeys()
				}
				val keys = response.map { unsplashImage ->
					UnsplashRemoteKeys(
						id = unsplashImage.id,
						prevPage = prevPage,
						nextPage = nextPage
					)
				}
				unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
				unsplashImageDao.addImages(images = response)
			}
			MediatorResult.Success(endOfPaginationReached = endOfPaginatedReached)

		} catch (e: Exception) {
			return MediatorResult.Error(throwable = e)
		}
	}

	private suspend fun getRemoteKeysClosestToCurrentPosition(
		state: PagingState<Int, UnsplashImage>,
	): UnsplashRemoteKeys? {
		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(anchorPosition = position)?.id?.let { id ->
				unsplashRemoteKeysDao.getRemoteKeys(id = id)
			}
		}
	}

	private suspend fun getRemoteKeysFirstItem(
		state: PagingState<Int, UnsplashImage>,
	): UnsplashRemoteKeys? {
		return state.pages.firstOrNull {
			it.data.isNotEmpty()
		}?.data?.firstOrNull()
			?.let { unsplashImage ->
				unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
			}
	}

	private suspend fun getRemoteKeyForLastItem(
		state: PagingState<Int, UnsplashImage>,
	): UnsplashRemoteKeys? {
		return state.pages.lastOrNull {
			it.data.isNotEmpty()
		}?.data?.lastOrNull()
			?.let { unsplashImage ->
				unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
			}
	}

}