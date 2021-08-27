package com.emptycoder.zaythal.api

import com.emptycoder.zaythal.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class apiClient{
    val URL = "https://zayapi.herokuapp.com/zay/"
    val apiInf: apiInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInf = retrofit.create(apiInterface::class.java)
    }

    fun addOwner(ownerName: String, phone:Int,storeName: String,location: String, password: Int): Call<String> {
        return apiInf.addOwner(ownerName,phone,storeName,location,password)
    }

    fun getOwner(phone:Int):Call<Onr>{
        return apiInf.getOwner(phone)
    }

    fun updateOwner(ownerId: String,ownerName: String, phone:Int,storeName: String,location: String, password: Int):Call<String>{
        return apiInf.updateOwner(ownerId,ownerName,phone,storeName,location, password)
    }

    fun deleteOwner(ownerId: String):Call<String>{
        return apiInf.deleteOwner(ownerId)
    }

    fun addCategory(ownerId: String,categoryName: String):Call<String>{
        return apiInf.addCategory(ownerId, categoryName)
    }

    fun updateCategory(categoryId: String, categoryName:String):Call<String>{
        return apiInf.updateCategory(categoryId,categoryName)
    }

    fun getCategory(password: String):Call<Category>{
        return apiInf.getCategory(password)
    }

    fun deleteCategory(ownerId: String,categoryId: String):Call<String>{
        return apiInf.deleteCategory(ownerId,categoryId)
    }

    fun addMenu(categoryId: String,menuName: String,price: Int):Call<String>{
        return apiInf.addMenu(categoryId,menuName, price)
    }

    fun updateMenu(menuId: String,menuName: String,price: Int):Call<String>{
        return apiInf.updateMenu(menuId,menuName, price)
    }

    fun getMenu(categoryId:String):Call<Menu>{
        return apiInf.getMenu(categoryId)
    }

    fun deleteMenu(categoryId: String,menuId: String):Call<String>{
        return apiInf.deleteMenu(categoryId,menuId)
    }

    fun addSale(ownerId:String,menuName:String,price: Int, quantity: Int, date:String):Call<String>{
        return apiInf.addSale(ownerId,menuName, price, quantity, date)
    }

    fun updateSale(saleId:String,menuName:String,price: Int, quantity: Int, date:String):Call<String>{
        return apiInf.updateSale(saleId,menuName, price, quantity, date)
    }

    fun getSale(salelistId: String):Call<Sale>{
        return apiInf.getSale(salelistId)
    }

    fun deleteSale(saleId: String, salelistId: String):Call<String>{
        return apiInf.deleteSale(saleId,salelistId)
    }

    fun getSaleList(ownerId: String):Call<Salelist>{
        return apiInf.getSaleList(ownerId)
    }

    fun deleteSaleList(ownerId: String,salelistId: String):Call<String>{
        return apiInf.deleteSaleList(ownerId,salelistId)
    }

}