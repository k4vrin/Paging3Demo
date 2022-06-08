package com.kavrin.paging3.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.kavrin.paging3.navigation.Screen
import com.kavrin.paging3.screens.common.ListContent

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
	navController: NavHostController,
	homeViewModel: HomeViewModel = hiltViewModel()
) {

	val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()

	Scaffold(
		topBar = {
			HomeTopBar(
				onSearchClicked = {
					navController.navigate(Screen.Search.route)
				}
			)
		},
		content = {
			ListContent(items = getAllImages)
		}
	)
}