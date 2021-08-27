package com.emptycoder.zaythal.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Sale(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("sales")
	val sales: List<SalesItem?>? = null
)

@Parcelize
data class SalesItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("menuName")
	val menuName: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
):Parcelable
