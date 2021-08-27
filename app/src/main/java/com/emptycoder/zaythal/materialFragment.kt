package com.emptycoder.zaythal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.emptycoder.zaythal.adapter.materialAdapter
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.FragmentMaterialBinding
import com.emptycoder.zaythal.model.Category
import com.emptycoder.zaythal.model.CategoryItem
import com.emptycoder.zaythal.model.Onr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class materialFragment : Fragment(),materialAdapter.OnMaterialClick{

    lateinit var binding: FragmentMaterialBinding

    lateinit var client: apiClient
    lateinit var pref: preferences
    lateinit var rc: materialAdapter
    var oId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMaterialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = preferences(requireContext())

        client = apiClient()
        rc = materialAdapter(requireContext(),this)

        val ph = pref.getInt(Constants.KEY_PHONE)
        client.getOwner(ph).enqueue(object : Callback<Onr>{
            override fun onResponse(call: Call<Onr>, response: Response<Onr>) {
                oId = response.body()!!.owner!!.id!!
                pref.putString(Constants.KEY_OWNER_ID,oId)
                client.getCategory(oId).enqueue(object : Callback<Category>{
                    override fun onResponse(call: Call<Category>, response: Response<Category>) {
                        rc.addCL(response.body()!!.category!! as ArrayList<CategoryItem>)
                        binding.materialRc.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                        binding.materialRc.adapter = rc
                    }

                    override fun onFailure(call: Call<Category>, t: Throwable) {
                        Log.d("getCategoryFial",t.message.toString())
                    }

                })

            }

            override fun onFailure(call: Call<Onr>, t: Throwable) {
                Log.d("FailOwner",t.message.toString())
            }
        })


       binding.addmaterialBtn.setOnClickListener {
           val dialog = MaterialDialog(requireContext())
                   .noAutoDismiss()
                   .customView(R.layout.dialogtype)

           val tname = dialog.findViewById<EditText>(R.id.materaialTypeInput).text
            dialog.findViewById<Button>(R.id.YesBtn).setOnClickListener {
                val id = pref.getString(Constants.KEY_OWNER_ID)
                addCat(id!!,tname.toString())
                dialog.dismiss()
            }
            dialog.findViewById<Button>(R.id.NoBtn).setOnClickListener {
                dialog.dismiss()
            }
           dialog.show()
       }

    }

    override fun materialClick(data: CategoryItem) {
         val action = materialFragmentDirections.actionMaterialFragmentToMaterialListFragment(data.id!!)
        findNavController().navigate(action)
    }

    fun addCat(id: String,name: String){
        client.addCategory(id,name).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("addCategorySuccess",response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("addCategoryFail",t.message.toString())
            }

        })
    }
}