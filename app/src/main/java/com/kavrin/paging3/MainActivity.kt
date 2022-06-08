package com.kavrin.paging3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.kavrin.paging3.navigation.SetupNavGraph
import com.kavrin.paging3.ui.theme.Paging3Theme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Paging3Theme {
				val navController = rememberNavController()
				SetupNavGraph(navHostController = navController)
			}
		}
	}
}