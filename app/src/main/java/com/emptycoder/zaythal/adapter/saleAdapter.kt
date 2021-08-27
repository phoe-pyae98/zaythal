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
import com.emptycoder.zaythal.databinding.SaleviewBinding
import com.emptycoder.zaythal.model.SalesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class saleAdapter(val slId: String, val context: Context, val t: TotalCount):RecyclerView.Adapter<saleAdapter.SaleViewHolder>() {

    lateinit var sl: ArrayList<SalesItem>


    fun addSale(data: ArrayList<SalesItem>){
        this.sl = data
    }

    interface TotalCount{
        fun total(t: Int)
    }

    inner class SaleViewHolder(val binding: SaleviewBinding):RecyclerView.ViewHolder(binding.root){
        var tc = 0
        fun bind(data: SalesItem,t: TotalCount){
            binding.saleviewName.setText(data.menuName)
            binding.saleviewPrice.setText(data.price.toString())
            binding.saleviewQuantity.setText(data.quantity.toString())
            tc += data.price!!
            t.total(tc)
            binding.changeBtn.setOnClickListener {
                popup(it,data,tc)
            }

        }

        fun popup(v: View,data: SalesItem,t: Int){
            val popupMenu = PopupMenu(context,v)
            popupMenu.inflate(R.menu.edit_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.updatemenu ->{
                        val dialog = MaterialDialog(context)
                                .noAutoDismiss()
                                .customView(R.layout.update_salesitem)

                        dialog.findViewById<EditText>(R.id.changesalequantity).setText(data.quantity.toString())
                        val q = dialog.findViewById<EditText>(R.id.changesalequantity).text.toString().toInt()

                        dialog.findViewById<Button>(R.id.updateSaleBtn).setOnClickListener {
                            val client = apiClient()
                            client.updateSale(data.id!!,data.menuName!!,data.price!!,q,data.date!!).enqueue(object : Callback<String>{
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Log.d("updateSaleSuccess",response.body().toString())
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                     Log.d("updateSaleFail",t.message.toString())
                                }

                            })
                            dialog.dismiss()
                        }

                        dialog.findViewById<Button>(R.id.CancelSaleBtn).setOnClickListener {
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
                            client.deleteSale(data.id!!,slId).enqueue(object :Callback<String>{
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Log.d("deleteSalesuccess",response.body().toString())
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d("deleteSaleFail",t.message.toString())
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SaleViewHolder((SaleviewBinding.inflate(layoutInflater,parent,false)))
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        holder.bind(sl[position],t)
    }

    override fun getItemCount(): Int {
        return sl.size
    }
}