package com.example.telemed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.telemed.databinding.ActivityEcoStartBinding

class EcoStart : AppCompatActivity() {

    lateinit var binding: ActivityEcoStartBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEcoStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnGetStarted=binding.btnGetStarted

        btnGetStarted.setOnClickListener{

            startActivity(Intent(this, LoginScreen::class.java))

        }

    }
}