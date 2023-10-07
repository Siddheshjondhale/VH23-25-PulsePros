package com.example.telemed

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.telemed.databinding.FragmentDoctorMainPro4Binding
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3" // Add this constant for email
class DoctorMainPro4 : Fragment() {
    private lateinit var binding: FragmentDoctorMainPro4Binding // Use data binding

    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorMainPro4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch and display data based on the email
        if (email != null) {
            fetchDoctorData(email!!)
        }
    }

    private fun fetchDoctorData(email: String) {
        // Initialize Firestore
        val db = FirebaseFirestore.getInstance()

        // Reference to the doctor's profile using the email
        val doctorProfileRef = db.collection("users")
            .document("Doctors")
            .collection("profile")
            .document(email)

        // Fetch data from Firestore
        doctorProfileRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Data found, update UI using data binding

                    val doctor = hashMapOf<String, String>()
                    doctor["name"] = documentSnapshot.getString("name") ?: ""
                    doctor["speciality"] = documentSnapshot.getString("speciality") ?: ""
                    doctor["regno"] = documentSnapshot.getString("regno") ?: ""
                    doctor["regyear"] = documentSnapshot.getString("regyear") ?: ""

                    // Bind the doctor object to the layout using data binding
                    binding.txtDrRuthBourne.text = doctor["name"]
                    binding.txtCardiologist.text = doctor["speciality"]
                    binding.txtRegisterationN.text = "Reg no :"+doctor["regno"]
                    binding.txtYearOfRegiste.text = "Reg year :"+doctor["regyear"]

                    Toast.makeText(requireContext(),doctor["speciality"] , Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(requireContext(),"doctorf" , Toast.LENGTH_SHORT).show()

                    // Handle the case where the document does not exist
                    // You can display an error message or handle it as needed
                }
            }
            .addOnFailureListener { e ->
                // Handle any errors that may occur during the fetch
                Log.w(TAG, "Error fetching doctor data", e)
            }
    }

    companion object {
        private const val ARG_PARAM3 = "param3"

        @JvmStatic
        fun newInstance(email: String) =
            DoctorMainPro4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM3, email)
                }
            }
    }
}
