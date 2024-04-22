package com.aftarfadilah.a160421095hobbyapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.aftarfadilah.a160421095hobbyapp.R
import com.aftarfadilah.a160421095hobbyapp.databinding.ActivityMainBinding
import com.aftarfadilah.a160421095hobbyapp.model.User


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    var LoggedInUserId: Number = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)

        navController = (supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.bottomNav.setupWithNavController(navController)
    }
}