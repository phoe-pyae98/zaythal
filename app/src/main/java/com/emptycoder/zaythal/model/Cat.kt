package com.emptycoder.zaythal.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryItem(

        @field:SerializedName("createdAt")
        val createdAt: String? = null,

        @field:SerializedName("__v")
        val V: Int? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("_id")
        val id: String? = null,

        @field:SerializedName("menu")
        val menu: List<String?>? = null,

        @field:SerializedName("updatedAt")
        val updatedAt: String? = null
): Parcelable
