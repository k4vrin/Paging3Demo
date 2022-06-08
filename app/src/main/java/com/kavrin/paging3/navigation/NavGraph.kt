package com.kavrin.paging3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.kavrin.paging3.screens.home.HomeScreen

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun SetupNavGraph(
	navHostController: NavHostController
) {
	NavHost(
		navController = navHostController,
		startDestination = Screen.Home.route
	) {
		composable(route = Screen.Home.route) {
			HomeScreen(navController = navHostController)
		}

		composable(route = Screen.Search.route) {
//			SearchScreen(navController = navHostController)
		}
	}
}