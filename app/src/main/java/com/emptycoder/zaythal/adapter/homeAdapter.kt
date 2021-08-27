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
import com.emptycoder.zaythal.R
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.HomercViewBinding
import com.emptycoder.zaythal.model.ListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class homeAdapter(val oId: String, val context: Context, private val listener: OnItemClick):RecyclerView.Adapter<homeAdapter.homeViewHolder>(){

    lateinit var salelist: ArrayList<ListItem>

    fun addSL(data: ArrayList<ListItem>){
        this.salelist = data
    }

    inner class homeViewHolder(val binding: HomercViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: ListItem,listener: OnItemClick){
            binding.dateTxt.setText(data.date)
            binding.edithome.setOnClickListener {
                popup(it,data)
            }
            itemView.setOnClickListener {
                listener.onClick(data)
            }
        }

        private fun popup(v: View, data: ListItem){
            val popupMenu = PopupMenu(context,v)
            popupMenu.inflate(R.menu.delete_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.deletehmenu -> {
                        val dialog = MaterialDialog(context)
                                .noAutoDismiss()
                                .customView(R.layout.deletehome)
                        dialog.findViewById<Button>(R.id.dslbtn).setOnClickListener {
                            val client = apiClient()
                            client.deleteSaleList(oId,data.id!!).enqueue(object : Callback<String>{
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Log.d("deletesalelistsuccess",response.body().toString())
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d("deletesalelistfail",t.message.toString())
                                }

                            })
                            dialog.dismiss()
                        }
                        dialog.findViewById<Button>(R.id.nodslbtn).setOnClickListener {
                            dialog.dismiss()
                        }
                        dialog.show()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }

            popupMenu.show()

        }
    }
    interface OnItemClick{
        fun onClick(sl: ListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        return homeViewHolder(HomercViewBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: homeViewHolder, position: Int) {
       holder.bind(salelist[position],listener)
    }

    override fun getItemCount(): Int {
        return salelist.size
    }
}