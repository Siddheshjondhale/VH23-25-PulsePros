
package com.example.telemed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.telemed.ChooseRegi
import com.example.telemed.MainActivity
import com.example.telemed.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val emailEditText = binding.txtEnteremail
        val passwordEditText = binding.txtEnterpassword
        val loginBtn = binding.btnLoginOne
        val goToregister = binding.goToregister

        loginBtn.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()


            Toast.makeText(this, password+email, Toast.LENGTH_SHORT).show()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Attempt to sign in with Firebase Authentication
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Login successful, go to MainActivity
                            startActivity(Intent(this, MainActivity::class.java))
                            finish() // Finish the current activity
                        } else {
                            // Login failed, show an error message
                            Toast.makeText(
                                this,
                                "Incorrect email or password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                // Email or password is empty, show an error message
                Toast.makeText(
                    this,
                    "Please enter both email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        goToregister.setOnClickListener {
            startActivity(Intent(this, ChooseRegi::class.java))
        }
    }
}
