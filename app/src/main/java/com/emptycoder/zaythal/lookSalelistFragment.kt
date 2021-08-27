package com.emptycoder.zaythal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.emptycoder.zaythal.adapter.saleAdapter
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.FragmentLookSalelistBinding
import com.emptycoder.zaythal.model.Sale
import com.emptycoder.zaythal.model.SalesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class lookSalelistFragment : Fragment(), saleAdapter.TotalCount {

    lateinit var binding: FragmentLookSalelistBinding
    lateinit var client: apiClient
    lateinit var rc: saleAdapter
    val data: lookSalelistFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentLookSalelistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        client = apiClient()
        rc = saleAdapter(data.idStr,requireContext(),this)
        client.getSale(data.idStr).enqueue(object : Callback<Sale>{
            override fun onResponse(call: Call<Sale>, response: Response<Sale>) {
                rc.addSale(response.body()!!.sales!! as ArrayList<SalesItem>)
                binding.looksaleRc.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.looksaleRc.adapter = rc
            }

            override fun onFailure(call: Call<Sale>, t: Throwable) {
                Log.d("getSaleFail",t.message.toString())
            }

        })
    }

    override fun total(t: Int) {
        binding.totalmoney.setText(t.toString())
    }


}