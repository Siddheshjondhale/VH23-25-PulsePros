package com.example.telemed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.telemed.databinding.FragmentPatientAppointmentUIBinding
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_DOCTOR_EMAIL = "doctor_email"

class PatientAppointmentUI : Fragment() {
    private lateinit var binding: FragmentPatientAppointmentUIBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientAppointmentUIBinding.inflate(inflater, container, false)

        val spinnerTimeSlot = binding.spinnerTimeSlot
        val timeSlotOptions = arrayOf(
            "9:00 AM - 10:00 AM",
            "10:00 AM - 11:00 AM",
            "11:00 AM - 12:00 PM",
            "2:00 PM - 3:00 PM",
            "3:00 PM - 4:00 PM"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeSlotOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimeSlot.adapter = adapter

        val submitButton = binding.BookAppointment
        submitButton.setOnClickListener {
            // Handle the button click here
            val patientName = binding.patName.text.toString()
            val patientEmail = binding.patEmail.text.toString()
            val patientProblem = binding.patProblem.text.toString()
            val selectedTimeSlot = binding.spinnerTimeSlot.selectedItem.toString()

            // Retrieve the doctor's email from the arguments
            val doctorEmail = arguments?.getString(ARG_DOCTOR_EMAIL)

            // Create and store the patient's appointment data in Firestore
            storePatientAppointment(patientName, patientEmail, patientProblem, selectedTimeSlot, doctorEmail)
        }

        return binding.root
    }

    private fun storePatientAppointment(
        patientName: String,
        patientEmail: String,
        patientProblem: String,
        selectedTimeSlot: String,
        doctorEmail: String?
    ) {
        // Create a map to store the patient's appointment data
        val appointmentData = hashMapOf(
            "name" to patientName,
            "email" to patientEmail,
            "problem" to patientProblem,
            "timeSlot" to selectedTimeSlot
        )

        // Add the appointment data to Firestore under the patient's profile
        db.collection("users")
            .document("Patients")
            .collection("Profile")
            .document(patientEmail)
            .collection("Appointments")
            .document(doctorEmail.toString())
            .set(appointmentData)
            .addOnSuccessListener {
                Toast.makeText(requireContext() , "Appointment Sucessfully Booked", Toast.LENGTH_SHORT).show()
                // Data has been successfully stored in Firestore
                // You can display a success message or navigate to another fragment/activity
            }
            .addOnFailureListener { e ->
                // Handle any errors that may occur during the Firestore operation
                // You can display an error message or log the error
            }

        // Update the doctor's Firestore database with the patient's appointment details
        if (doctorEmail != null) {
            val doctorAppointmentData = hashMapOf(
                "patientEmail" to patientEmail,
                "patientName" to patientName,
                "problem" to patientProblem,
                "timeSlot" to selectedTimeSlot
            )

            db.collection("users")
                .document("Doctors")
                .collection("profile")
                .document(doctorEmail)
                .collection("Appointments")
                .document(patientEmail)
                .set(doctorAppointmentData)
                .addOnSuccessListener {
                    // Data has been successfully stored in the doctor's Firestore
                }
                .addOnFailureListener { e ->
                    // Handle any errors that may occur during the Firestore operation
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(doctorEmail: String) =
            PatientAppointmentUI().apply {
                arguments = Bundle().apply {
                    putString(ARG_DOCTOR_EMAIL, doctorEmail) // Store the doctor's email in the arguments
                }
            }
    }
}
