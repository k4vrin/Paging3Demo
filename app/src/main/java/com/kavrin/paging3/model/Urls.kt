package com.kavrin.paging3.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Urls(
	@SerialName("regular")
	val regularImage: String
)
