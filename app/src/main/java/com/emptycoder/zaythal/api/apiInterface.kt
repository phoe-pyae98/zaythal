package com.emptycoder.zaythal.api


import com.emptycoder.zaythal.model.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface apiInterface {
//
    @POST("addOwner")
    fun addOwner(
        @Query("ownerName") ownerName: String,
        @Query("phone") phone: Int,
        @Query("storeName") storeName: String,
        @Query("location") location: String,
        @Query("password") password: Int
    ):Call<String>

    @GET("getOwner")
    fun getOwner(
        @Query("phone") phone: Int
    ):Call<Onr>

    @PUT("updateOwner")
    fun updateOwner(
            @Query("ownerId") ownerId: String,
            @Query("name") ownerName: String,
            @Query("phone") phone: Int,
            @Query("storeName") storeName: String,
            @Query("location") location: String,
            @Query("password") password: Int
    ):Call<String>

    @DELETE("deleteOwner")
    fun deleteOwner(
            @Query("ownerId") ownerId: String
    ):Call<String>


    @POST("addCategory")
    fun  addCategory(
            @Query("ownerId") ownerId: String,
            @Query("categoryName") categoryName: String
    ):Call<String>

    @PUT("updateCategory")
    fun updateCategory(
            @Query("categoryId") categoryId: String,
            @Query("categoryName") categoryName: String
    ):Call<String>

    @GET("getCategory")
    fun getCategory(
            @Query("password") password: String
    ):Call<Category>

    @DELETE("deleteCategory")
    fun deleteCategory(
            @Query("ownerId") ownerId: String,
            @Query("categoryId") categoryId: String
    ):Call<String>


    @POST("addMenu")
    fun addMenu(
            @Query("categoryId") categoryId: String,
            @Query("menuName") menuName: String,
            @Query("price") price: Int
    ):Call<String>

    @PUT("updateMenu")
    fun updateMenu(
            @Query("menuId") menuId: String,
            @Query("menuName") menuName: String,
            @Query("price") price: Int
    ):Call<String>

    @GET("getMenu")
    fun getMenu(
            @Query("categoryId") categoryId: String
    ):Call<Menu>

    @DELETE("deleteMenu")
    fun deleteMenu(
            @Query("categoryId") categoryId: String,
            @Query("menuId") menuId: String
    ):Call<String>


    @POST("addSale")
    fun addSale(
            @Query("ownerId") ownerId: String,
            @Query("menuName") menuName: String,
            @Query("price") price: Int,
            @Query("quantity") quantity: Int,
            @Query("date") date: String
    ):Call<String>

    @PUT("updateSale")
    fun updateSale(
            @Query("saleId") saleId: String,
            @Query("menuName") menuName: String,
            @Query("price")price: Int,
            @Query("quantity") quantity: Int,
            @Query("date") date: String
    ):Call<String>

    @GET("getSale")
    fun getSale(
            @Query("salelistId") salelistId: String
    ):Call<Sale>

    @DELETE("deleteSale")
    fun deleteSale(
            @Query("saleId") saleId: String,
            @Query("salelistId") salelistId: String
    ):Call<String>

    @GET("getSaleList")
    fun getSaleList(
            @Query("ownerId") ownerId: String
    ):Call<Salelist>

    @DELETE("deleteSaleList")
    fun deleteSaleList(
            @Query("ownerId") ownerId: String,
            @Query("salelistId") salelistId: String
    ):Call<String>
}