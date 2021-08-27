package com.emptycoder.zaythal

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emptycoder.zaythal.adapter.homeAdapter
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.FragmentHomeBinding
import com.emptycoder.zaythal.model.ListItem
import com.emptycoder.zaythal.model.Onr
import com.emptycoder.zaythal.model.Salelist
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(),homeAdapter.OnItemClick{

    lateinit var binding: FragmentHomeBinding
    lateinit var prefProvider: preferences
    var phone: Int =0
    lateinit var client: apiClient

    lateinit var rc : homeAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefProvider = preferences(requireContext())
        val ph = prefProvider.getInt(Constants.KEY_PHONE)
        val oid = prefProvider.getString(Constants.KEY_OWNER_ID)
        client = apiClient()
        rc = homeAdapter(oid!!,requireContext(),this)

        client.getOwner(ph).enqueue(object : Callback<Onr>{
            override fun onResponse(call: Call<Onr>, response: Response<Onr>) {
                val oId = response.body()!!.owner!!.id!!

                client.getSaleList(oId).enqueue(object :Callback<Salelist>{
                    override fun onResponse(call: Call<Salelist>, response: Response<Salelist>) {
                        rc.addSL(response.body()!!.list as ArrayList<ListItem>)
                        binding.sellRc.adapter = rc
                        binding.sellRc.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                    }

                    override fun onFailure(call: Call<Salelist>, t: Throwable) {
                        Log.d("getSaleListFail",t.message.toString())
                    }

                })
            }

            override fun onFailure(call: Call<Onr>, t: Throwable) {
                Log.d("homeOwnerFail",t.message.toString())
            }

        })

        binding.addSellListBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_materialFragment)
        }
    }

    override fun onClick(sl: ListItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToLookSalelistFragment(sl.id!!)
        findNavController().navigate(action)
    }


}