package com.assignment.temperature.datamodel

import com.google.gson.annotations.SerializedName

data class SourcesItem(

	@field:SerializedName("crawl_rate")
	val crawlRate: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)