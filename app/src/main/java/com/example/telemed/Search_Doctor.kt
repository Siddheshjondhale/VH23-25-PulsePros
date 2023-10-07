package com.example.telemed

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telemed.databinding.FragmentSearchDoctorBinding
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3" // Add this constant for email

class Search_Doctor : Fragment(), DoctorAdapter.OnItemClickListener {

    private lateinit var adapter: DoctorAdapter
    private val doctorsList = mutableListOf<Doctor>()

    private lateinit var binding: FragmentSearchDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DoctorAdapter(doctorsList, this)
        binding.doctorsRecyclerView.adapter = adapter
        binding.doctorsRecyclerView.layoutManager = LinearLayoutManager(context)

        populateDoctorsList()
    }

    private fun populateDoctorsList() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document("Doctors")
            .collection("profile")
            .get()
            .addOnSuccessListener { result ->
                // Clear the existing list to avoid duplicate entries
                doctorsList.clear()

                for (document in result) {
                    val name = document.getString("name") ?: ""
                    val speciality = document.getString("speciality") ?: ""
                    val emailID = document.getString("email") ?: ""
                    val doctor = Doctor(name, speciality, emailID)
                    doctorsList.add(doctor)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting doctors", exception)
            }
    }

    override fun onItemClick(position: Int) {
        val selectedDoctor = doctorsList[position]

        // Pass selectedDoctor data to DoctorMainPro4 fragment and navigate to it
        val doctorMainPro4Fragment = DoctorMainPro4.newInstance(selectedDoctor.email)


        parentFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, doctorMainPro4Fragment)
            .addToBackStack(null)
            .commit()
    }
}
