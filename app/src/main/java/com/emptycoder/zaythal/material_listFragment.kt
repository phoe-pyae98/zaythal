package com.emptycoder.zaythal

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.emptycoder.zaythal.adapter.addSellAdapter
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.FragmentMaterialListBinding
import com.emptycoder.zaythal.model.Menu
import com.emptycoder.zaythal.model.MenuItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class material_listFragment : Fragment(),addSellAdapter.OnAddSellClick {


    lateinit var binding: FragmentMaterialListBinding
    val data: material_listFragmentArgs by navArgs()
    lateinit var client: apiClient
    lateinit var rc: addSellAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMaterialListBinding.inflate(inflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cId = data.str
        client = apiClient()

        binding.addsinglematerialBtn.setOnClickListener {
            val dialog = MaterialDialog(requireContext())
                    .noAutoDismiss()
                    .customView(R.layout.material_add)

            dialog.findViewById<Button>(R.id.AddMaterialBtn).setOnClickListener {
                val name = dialog.findViewById<EditText>(R.id.addmnTxt).text.toString()
                val price = dialog.findViewById<EditText>(R.id.addmpTxt).text.toString().toInt()
                Log.d("materialName",data.str)
                if(price!= null) {
                    client.addMenu(data.str!!, name, price).enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            Log.d("AddMenuSuccess", response.body().toString())
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.d("AddMenuFail", t.message.toString())
                        }

                    })
                }else{
                    Log.d("inputNeed","Please input price")
                }


                dialog.dismiss()
            }
            dialog.findViewById<Button>(R.id.cancelBtn).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        rc = addSellAdapter(data.str,requireContext(),this)
        Log.d("sentDate>>",cId)
        client.getMenu(cId).enqueue(object : Callback<Menu>{
            override fun onResponse(call: Call<Menu>, response: Response<Menu>) {
                rc.addASL(response.body()!!.menu!! as ArrayList<MenuItem>)
                binding.mlistRc.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.mlistRc.adapter = rc
            }

            override fun onFailure(call: Call<Menu>, t: Throwable) {

            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun addSellClick(data: MenuItem) {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.buymaterial)

        val d = LocalDate.now()
        val date = (DateTimeFormatter.ofPattern("dd/MM/yyyy").format(d))


        dialog.findViewById<TextView>(R.id.mName).setText(data.name)
        dialog.findViewById<TextView>(R.id.mPrice).setText(data.price.toString())
        var quantity = dialog.findViewById<TextView>(R.id.quantity).text.toString().toInt()
        dialog.findViewById<Button>(R.id.qadd).setOnClickListener {
            quantity += 1
            dialog.findViewById<TextView>(R.id.quantity).setText(quantity.toString())
        }

            dialog.findViewById<Button>(R.id.qminus).setOnClickListener {
                if(quantity >1) {
                    quantity -= 1
                    dialog.findViewById<TextView>(R.id.quantity).setText(quantity.toString())
                }else{
                    quantity = 1
                    dialog.findViewById<TextView>(R.id.quantity).setText(quantity.toString())
                }
            }


        dialog.findViewById<Button>(R.id.buyBtn).setOnClickListener {
            client = apiClient()
            val pf = preferences(requireContext())
            val oId = pf.getString(Constants.KEY_OWNER_ID)
            val cost = data.price!!*quantity
            client.addSale(oId!!,data.name!!,cost,quantity,date).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("AddSaleSuccess",response.body().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("AddSaleFail",t.message.toString())
                }

            })

            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.nobuyBtn).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}