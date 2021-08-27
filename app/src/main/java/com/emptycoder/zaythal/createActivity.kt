package com.emptycoder.zaythal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.emptycoder.zaythal.databinding.ActivityCreateBinding
import com.google.android.material.navigation.NavigationView


class createActivity : AppCompatActivity() {

    lateinit var prefProvider : preferences
    lateinit var binding: ActivityCreateBinding

    lateinit var navHost: NavHostFragment
    lateinit var navController: NavController
    lateinit var config : AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val nav= findViewById<NavigationView>(R.id.slideNavi)
        navHost = supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHost.navController

        config = AppBarConfiguration(setOf(R.id.homeFragment,R.id.material_listFragment,R.id.materialFragment,R.id.aboutusFragment,R.id.profileFragment,R.id.developerFragment),binding.dl)
        toolbar.setupWithNavController(navController,config)
        nav.setupWithNavController(navController,)

        binding.slideNavi.itemIconTintList = null

    }
}