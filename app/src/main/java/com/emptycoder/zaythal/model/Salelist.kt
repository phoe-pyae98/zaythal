package com.emptycoder.zaythal.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Salelist(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("list")
	val list: List<ListItem?>? = null
)

@Parcelize
data class ListItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("salelist")
	val salelist: List<String?>? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
):Parcelable
