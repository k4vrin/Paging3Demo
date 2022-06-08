package com.kavrin.paging3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kavrin.paging3.data.local.dao.UnsplashImageDao
import com.kavrin.paging3.data.local.dao.UnsplashRemoteKeysDao
import com.kavrin.paging3.model.UnsplashImage
import com.kavrin.paging3.model.UnsplashRemoteKeys

@Database(
	entities = [UnsplashImage::class, UnsplashRemoteKeys::class],
	version = 1,
	exportSchema = true
)
abstract class UnsplashDatabase : RoomDatabase() {

	abstract fun unsplashImageDao(): UnsplashImageDao
	abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}