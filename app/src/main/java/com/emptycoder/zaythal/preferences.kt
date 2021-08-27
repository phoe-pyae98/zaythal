package com.emptycoder.zaythal

import android.content.Context

class preferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("myPreferences",0)

    fun putInt(key: String, value: Int){
        sharedPreferences.edit().putInt(key,value).apply()
    }

    fun getInt(key:String): Int{
        return sharedPreferences.getInt(key,0)
    }

    fun putString(key:String,value: String){
        sharedPreferences.edit().putString(key,value).apply()
    }

    fun getString(key: String):String?{
        return sharedPreferences.getString(key,null)
    }
}