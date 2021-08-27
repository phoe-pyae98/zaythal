package com.emptycoder.zaythal.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Category(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("category")
	val category: List<CategoryItem?>? = null
)

