package com.emptycoder.zaythal.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.emptycoder.zaythal.Constants
import com.emptycoder.zaythal.R
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.TypeViewBinding
import com.emptycoder.zaythal.model.CategoryItem
import com.emptycoder.zaythal.preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class materialAdapter( val context: Context,val listener: OnMaterialClick):RecyclerView.Adapter<materialAdapter.materialViewHolder>(){

    lateinit var categoryList: ArrayList<CategoryItem>

    fun addCL(data: ArrayList<CategoryItem>){
        this.categoryList = data
    }

    interface OnMaterialClick{
        fun materialClick(data: CategoryItem)
    }

    inner class materialViewHolder(val binding: TypeViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: CategoryItem,listener: OnMaterialClick){
            binding.typeNameTxt.setText(data.name)
            binding.editType.setOnClickListener {
                popUp(it,data)
            }
            itemView.setOnClickListener {
                listener.materialClick(data)
            }
        }

        private fun popUp(v: View, data: CategoryItem){
            val popupMenu = PopupMenu(context,v)
            popupMenu.inflate(R.menu.edit_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.updatemenu ->{
                        val dialog = MaterialDialog(context)
                                .noAutoDismiss()
                                .customView(R.layout.update_type)

                        dialog.findViewById<EditText>(R.id.changetypename).setText(data.name)

                        dialog.findViewById<Button>(R.id.updatetypebtn).setOnClickListener {
                            val name =  dialog.findViewById<EditText>(R.id.changetypename).text.toString()
                            val client = apiClient()
                            client.updateCategory(data.id!!,name!!).enqueue(object : Callback<String> {
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Log.d("updateTypeSuccess",response.body().toString())
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d("updateTypeFail",t.message.toString())
                                }

                            })
                            dialog.dismiss()
                        }

                        dialog.findViewById<Button>(R.id.canceltypebtn).setOnClickListener {
                            dialog.dismiss()
                        }

                        dialog.show()
                        true
                    }
                    R.id.deletemenu -> {
                        val dialog = MaterialDialog(context)
                                .noAutoDismiss()
                                .customView(R.layout.delete_sale)

                        dialog.findViewById<Button>(R.id.delelteSaleBtn).setOnClickListener {
                            val client = apiClient()
                            val oId = preferences(context).getString(Constants.KEY_OWNER_ID)
                            client.deleteCategory(oId!!,data.id!!).enqueue(object : Callback<String> {
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Log.d("deleteTypesuccess",response.body().toString())
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d("deleteTypeFail",t.message.toString())
                                }

                            })
                            dialog.dismiss()
                        }

                        dialog.findViewById<Button>(R.id.canceldelete).setOnClickListener {
                            dialog.dismiss()
                        }
                        dialog.show()
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
        }

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): materialViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return materialViewHolder((TypeViewBinding.inflate(layoutInflater, parent, false)))
    }

    override fun onBindViewHolder(holder: materialViewHolder, position: Int) {
        holder.bind(categoryList[position],listener)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}