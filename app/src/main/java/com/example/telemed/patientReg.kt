package com.example.telemed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.telemed.databinding.ActivityPatientRegBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class patientReg : AppCompatActivity() {
    lateinit var binding: ActivityPatientRegBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fname = binding.Fname
        val lname = binding.Lname
        val emailDoc = binding.EmailPat
        val passDoc = binding.PassPat

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        val registerButton = binding.btnRegister
        registerButton.setOnClickListener {
            // Get user input data
            val firstName = fname.text.toString()
            val lastName = lname.text.toString()
            val userEmail = emailDoc.text.toString()
            val userPassword = passDoc.text.toString()

            // Create a new user with email and password
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // User registration successful, get the user's UID
                        val user = auth.currentUser
                        val uid = user?.uid

                        // Create a data map to store user information
                        val userMap = hashMapOf(
                            "UIID" to uid,
                            "Fname" to firstName,
                            "Lname" to lastName,
                            "email" to userEmail
                        )

                        // Store user information in Firestore
                        firestore.collection("users")
                            .document("Patients")
                            .collection("Profile")
                            .document(userEmail)
                            .set(userMap)
                            .addOnSuccessListener {
                                    // Data saved successfully
                                    // You can add further actions here if needed
                                    // For example, navigate to the next activity
                                     startActivity(Intent(this, LoginScreen::class.java))
                            }
                            .addOnFailureListener { e ->
                                // Error occurred while saving data
                                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        // User registration failed
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }






    }
}