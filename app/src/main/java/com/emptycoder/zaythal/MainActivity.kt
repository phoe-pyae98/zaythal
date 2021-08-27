package com.emptycoder.zaythal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.emptycoder.zaythal.api.apiClient
import com.emptycoder.zaythal.databinding.ActivityMainBinding
import com.emptycoder.zaythal.model.Onr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var prefProvider: preferences
    lateinit var client: apiClient
    var str: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        client = apiClient()


        prefProvider = preferences(applicationContext)

        val load = prefProvider.getString(Constants.KEY_FIRST_TIME)

        if (load.equals("Yes")) {
            val intent = Intent(this, createActivity::class.java)
            startActivity(intent)
            finish()
        } else {

            binding.ownerCreateBtn.setOnClickListener {

                val name = binding.inputName.text.toString()
                val phone = binding.inputPhone.text.toString().toIntOrNull()
                val stName = binding.inputStoreName.text.toString()
                val location = binding.inputLocation.text.toString()
                val pass = binding.inputPassword.text.toString().toIntOrNull()


                if (name != "" && phone != null && stName != "" && location != ""
                        && pass != null) {

                    val api = client.addOwner(name, phone!!, stName, location, pass!!)
                    api.enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            str = response.body().toString()
                            prefProvider.putString(Constants.KEY_FIRST_TIME, "Yes")
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            str = t.message.toString()
                            prefProvider.putString(Constants.KEY_FIRST_TIME, "No")
                        }
                    })
                    val check = prefProvider.getString(Constants.KEY_FIRST_TIME)

                    client.getOwner(phone).enqueue(object : Callback<Onr>{
                        override fun onResponse(call: Call<Onr>, response: Response<Onr>) {
                            val oId = response.body()!!.owner!!.id
                            prefProvider.putString(Constants.KEY_OWNER_ID,oId!!)
                        }

                        override fun onFailure(call: Call<Onr>, t: Throwable) {
                            Log.d("ownerFail",t.message.toString())
                        }

                    })
                    if(check.equals("Yes")) {
                        prefProvider.putInt(Constants.KEY_PHONE, phone!!)
                        val intent = Intent(this, createActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,str,Toast.LENGTH_LONG).show()
                    }

                } else {
                    prefProvider.putString(Constants.KEY_FIRST_TIME,"No")
                    Toast.makeText(this,"All fields are required,Please Fill!",Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}