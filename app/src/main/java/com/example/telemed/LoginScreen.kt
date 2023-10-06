package com.example.telemed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telemed.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {
    lateinit var binding: ActivityLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var loginBtn=binding.btnLoginOne
var goToregister= binding.goToregister

loginBtn.setOnClickListener{
    startActivity(Intent(this, LoginScreen::class.java))

}
        goToregister.setOnClickListener{
            startActivity(Intent(this, ChooseRegi::class.java))

        }


    }
}