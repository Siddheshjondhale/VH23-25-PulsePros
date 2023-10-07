package com.example.telemed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.telemed.databinding.ActivityMainBinding
import homeFragment

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set the default fragment to homeFragment when the activity starts
        inflateFragment(homeFragment())


        binding.bottomBar.selectedItemId = R.id.nav_home

        binding.bottomBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    inflateFragment(homeFragment())
                }
                R.id.nav_dashboard -> {
                    inflateFragment(homeFragment())
                }
                R.id.nav_guard -> {
                    inflateFragment(homeFragment())
                }
                R.id.nav_profile -> {
                    inflateFragment(DoctorMainPro4())
                }
            }
            true
        }







    }//end of oncreate function


    private fun inflateFragment(newInstance: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContainer, newInstance)
        transaction.commit()
    }





}  //end of class tage



