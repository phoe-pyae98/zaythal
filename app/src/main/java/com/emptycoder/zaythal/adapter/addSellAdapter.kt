package com.emptycoder.zaythal.adapter
//
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
import com.emptycoder.zaythal.databinding.AddsellrcViewBinding
import com.emptycoder.zaythal.model.MenuItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class addSellAdapter(val cId: String,val context: Context, val listener: OnAddSellClick): RecyclerView.Adapter<addSellAdapter.addSellViewHolder>(){

    lateinit var menuList: ArrayList<MenuItem>

    fun addASL(data: ArrayList<MenuItem>){
        this.menuList = data
    }

    interface OnAddSellClick{
        fun addSellClick(data: MenuItem)
    }

    inner class addSellViewHolder(val binding: AddsellrcViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: MenuItem, listener: OnAddSellClick){
            binding.asmaterialName.setText(data.name)
            binding.asmaterialPrice.setText(data.price.toString())
            binding.asBtn.setOnClickListener {
                listener.addSellClick(data)
            }
            binding.editaddsell.setOnClickListener {
                popUp(it,data)
            }
        }

        private fun popUp(v: View, data: MenuItem){
            val popupMenu = PopupMenu(context,v)
            popupMenu.inflate(R.menu.edit_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.updatemenu ->{
                        val dialog = MaterialDialog(context)
                                .noAutoDismiss()
                                .customView(R.layout.updatemateriallist)
                        dialog.findViewById<EditText>(R.id.updatematerialName).setText(data.name)
                        dialog.findViewById<EditText>(R.id.updatematerialPrice).setText(data.price.toString())

                        dialog.findViewById<Button>(R.id.umaterialBtn).setOnClickListener {

                            val client = apiClient()
                            val name = dialog.findViewById<EditText>(R.id.updatematerialName).text.toString()
                            val price = dialog.findViewById<EditText>(R.id.updatematerialPrice).text.toString().toInt()
                            client.updateMenu(data.id!!,name,price).enqueue(object : Callback<String>{
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Log.d("updateMenuSuccess",response.body().toString())
                                    Log.d("seeinput",name)
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d("updateMenuFail",t.message.toString())
                                }

                            })
                            dialog.dismiss()
                        }
                        dialog.show()
                        true
                    }
                    R.id.deletemenu -> {
                        val dialog = MaterialDialog(context)
                                .noAutoDismiss()
                                .customView(R.layout.deletemenu)

                        dialog.findViewById<Button>(R.id.mdyesbtn).setOnClickListener {
                            val client = apiClient()
                            client.deleteMenu(cId,data.id!!).enqueue(object : Callback<String>{
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Log.d("deleteMenuSuccess",response.body().toString())
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d("deleteMenuSuccess",t.message.toString())
                                }

                            })
                            dialog.dismiss()
                        }

                        dialog.findViewById<Button>(R.id.ndmenubtn).setOnClickListener {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): addSellViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return addSellViewHolder((AddsellrcViewBinding.inflate(layoutInflater,parent,false)))
    }

    override fun onBindViewHolder(holder: addSellViewHolder, position: Int) {
        holder.bind(menuList[position],listener)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}