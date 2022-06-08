package com.kavrin.paging3.di

import android.app.Application
import androidx.room.Room
import com.kavrin.paging3.data.local.UnsplashDatabase
import com.kavrin.paging3.util.Constants.UNSPLASH_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Provides
	@Singleton
	fun provideDatabase(
		app: Application,
	): UnsplashDatabase {
		return Room.databaseBuilder(
			app,
			UnsplashDatabase::class.java,
			UNSPLASH_DATABASE
		).build()
	}

}