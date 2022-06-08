package com.kavrin.paging3.navigation

sealed class Screen(val route: String) {
	object Home : Screen(route = "home_screen")
	object Search : Screen(route = "search_screen")
}
