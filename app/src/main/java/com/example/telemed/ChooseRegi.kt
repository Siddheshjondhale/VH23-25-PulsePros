package com.example.telemed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telemed.databinding.ActivityChooseRegiBinding
import com.example.telemed.databinding.ActivityLoginScreenBinding

class ChooseRegi : AppCompatActivity() {
    lateinit var binding: ActivityChooseRegiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseRegiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var doctorRegPage=binding.doctorRegPage
 var pateintRegpage=binding.pateintRegpage


        doctorRegPage.setOnClickListener{

        }

        pateintRegpage.setOnClickListener{

        }


    }
}