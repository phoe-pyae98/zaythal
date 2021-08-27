package com.emptycoder.zaythal.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Onr(

		@field:SerializedName("request")
		val request: String? = null,

	@field:SerializedName("owner")
	val owner: Owner? = null

)

@Parcelize
data class Owner(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: Int? = null,

	@field:SerializedName("phone")
	val phone: Int? = null,

	@field:SerializedName("saleList")
	val saleList: List<String?>? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("categoryList")
	val categoryList: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("storeName")
	val storeName: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
): Parcelable
