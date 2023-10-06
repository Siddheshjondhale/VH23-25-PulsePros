package com.example.telemed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.telemed.databinding.ActivityDoctorProfBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class DoctorProf : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorProfBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    private var imageUri: Uri? = null // Declare the imageUri variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorProfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set OnClickListener for the "Upload Photo" button
        binding.textView2.setOnClickListener {
            // Launch an image picker intent to select an image from the device's storage
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val genderGroup = binding.genderGroup // Your RadioGroup
        var selectedGender = "" // To store the selected gender

        // Listen for changes in the selected radio button
        genderGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            selectedGender = selectedRadioButton.text.toString()
        }

        // Handle data submission (e.g., when the user clicks the "Register" button)
        binding.button.setOnClickListener {
            // Collect user input data
            val name = binding.docName.text.toString()
            val email = binding.EmailDoc.text.toString()
            val age = binding.ageDoc.text.toString()
            val gender = selectedGender.toString()
            val speciality = binding.SpecialityDoc.text.toString()

            // Validate input fields (you can add your validation logic here)

            // You can now use the 'imageUri' variable to access the selected image
            if (imageUri != null) {
                // You can perform further actions with 'imageUri' here

                // For example, you can display the image in an ImageView:
                // binding.imageView.setImageURI(imageUri)

                // Or upload it to Firebase Storage or perform any other desired operations
            }

            // Store other data in Firestore
            if (currentUser != null) {
                val currentUserEmail = currentUser.email ?: ""

                val doctorData = hashMapOf(
                    "name" to name,
                    "age" to age,
                    "gender" to selectedGender.toString(),
                    "speciality" to speciality
                )

                // Update specific fields in the existing Firestore document
                db.collection("users")
                    .document("Doctors")
                    .collection("profile")
                    .document(currentUserEmail)
                    .update(doctorData as Map<String, Any>)
                    .addOnSuccessListener {
                        // Data was successfully updated in Firestore
                        Toast.makeText(
                            this@DoctorProf,
                            "Doctor profile updated successfully.",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(Intent(this, LoginScreen::class.java))
                    }
                    .addOnFailureListener { e ->
                        // Handle any errors that occurred during the Firestore update operation
                        Toast.makeText(
                            this@DoctorProf,
                            "Error updating doctor profile: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageUri = data?.data // Assign the selected image Uri to imageUri
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
