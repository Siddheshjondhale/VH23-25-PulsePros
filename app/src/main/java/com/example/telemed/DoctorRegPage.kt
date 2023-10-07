package com.example.telemed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.telemed.databinding.ActivityDoctorRegPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DoctorRegPage : AppCompatActivity() {
    lateinit var binding: ActivityDoctorRegPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorRegPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val emailDoc = binding.txtEnteremail
        val passDoc = binding.txtEnterpassword
        val regno = binding.RegDoctorNo
        val regyear = binding.RegDoctoryear

        binding.btnRegister.setOnClickListener {
            val email = emailDoc.text.toString()
            val password = passDoc.text.toString()
            val doctorNo = regno.text.toString()
            val doctorYear = regyear.text.toString()

            // Register the user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        // User registration successful
                        // Now, store user details in Firestore
                        if (user != null) {
                            val doctorData = hashMapOf(
                                "UId" to user.uid,
                                "email" to email,
                                "password" to password, // Note: Never store plaintext passwords
                                "regno" to doctorNo,
                                "regyear" to doctorYear
                            )

                            firestore.collection("users")
                                .document("Doctors")
                                .collection("profile")
                                .document(email) // Using email as the document ID
                                .set(doctorData)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Registration successful and data saved to Firestore.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, DoctorProf::class.java))

                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        "Registration successful but failed to save data to Firestore: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Registration failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
