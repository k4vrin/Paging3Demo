package com.kavrin.paging3.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import com.kavrin.paging3.ui.theme.topAppBarBgColor
import com.kavrin.paging3.ui.theme.topAppBarContentColor

@Composable
fun HomeTopBar(
	onSearchClicked: () -> Unit
) {
	TopAppBar(
		title = {
			Text(
				text = "Home",
				color = MaterialTheme.colors.topAppBarContentColor
			)
		},
		backgroundColor = MaterialTheme.colors.topAppBarBgColor,
		actions = {
			IconButton(onClick = onSearchClicked) {
				Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
			}
		}
	)

}