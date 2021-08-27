package com.emptycoder.zaythal

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.FragmentProfileBinding
import com.emptycoder.zaythal.model.Onr
import com.emptycoder.zaythal.model.Owner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    lateinit var pref : preferences
    lateinit var client: apiClient
    lateinit var p: Owner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = preferences(requireContext())

        val ph = pref.getInt(Constants.KEY_PHONE)
        client = apiClient()
        val api = client.getOwner(ph)
        api.enqueue(object: Callback<Onr>{
            override fun onResponse(call: Call<Onr>, response: Response<Onr>) {
                binding.profileName.setText(response.body()!!.owner!!.name)
                binding.phone.setText(response.body()!!.owner!!.phone.toString())
                binding.storename.setText(response.body()!!.owner!!.storeName)
                binding.location.setText(response.body()!!.owner!!.location)
                binding.password.setText(response.body()!!.owner!!.password.toString())
                p = response.body()!!.owner!!
            }

            override fun onFailure(call: Call<Onr>, t: Throwable) {
                Log.d("res>>",t.message.toString())
            }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.updateProfileFragment -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment(p)
                findNavController().navigate(action)
                return true
            }
      }
        return super.onOptionsItemSelected(item)
    }
}