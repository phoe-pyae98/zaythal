package com.emptycoder.zaythal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.FragmentUpdateProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileFragment : Fragment() {

    lateinit var binding: FragmentUpdateProfileBinding
    lateinit var client: apiClient
    var str = ""

    val o : UpdateProfileFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentUpdateProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client = apiClient()


        binding.updateownerName.setText(o.data.name)
        binding.updateownerPhone.setText(o.data.phone.toString())
        binding.updateownerStoreName.setText(o.data.storeName)
        binding.updateownerLocation.setText(o.data.location)
        binding.updateownerPassword.setText(o.data.password.toString())


        binding.updateOwnerBtn.setOnClickListener {

            val name = binding.updateownerName.text.toString()
            val phone = binding.updateownerPhone.text.toString().toIntOrNull()
            val storeName = binding.updateownerStoreName.text.toString()
            val location = binding.updateownerLocation.text.toString()
            val pass = binding.updateownerPassword.text.toString().toIntOrNull()

            val api = client.updateOwner(o.data.id!!,name,phone!!,storeName,location,pass!!)
            api.enqueue(object: Callback<String>{
               override fun onResponse(call: Call<String>, response: Response<String>) {
                   str = response.body().toString()
               }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    str = t.message.toString()
               }

        })
          findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
        }


    }


}