package com.example.telemed

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.telemed.databinding.FragmentDoctorMainPro4Binding
import com.example.telemed.databinding.FragmentPatientAppointmentUIBinding
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PatientAppointmentUI : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val db = FirebaseFirestore.getInstance()


    lateinit var binding: FragmentPatientAppointmentUIBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientAppointmentUIBinding.inflate(inflater, container, false)



        val spinnerTimeSlot = binding.spinnerTimeSlot

        // Set up the Spinner adapter
        val timeSlotOptions = arrayOf("9:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM", "2:00 PM - 3:00 PM", "3:00 PM - 4:00 PM")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, timeSlotOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimeSlot.adapter = adapter



            val submitButton = binding.BookAppointment

            submitButton.setOnClickListener {
                // Handle the button click here
                val patientName = binding.patName.text.toString()
                val patientEmail = binding.patEmail.text.toString()
                val patientProblem = binding.patProblem.text.toString()
                val selectedTimeSlot = binding.spinnerTimeSlot.selectedItem.toString()

                // Create and store the data in Firestore
                storeAppointmentData(patientName, patientEmail, patientProblem, selectedTimeSlot)
            }



        return binding.root
    }


private fun storeAppointmentData(
    patientName: String,
    patientEmail: String,
    patientProblem: String,
    selectedTimeSlot: String
) {
    // Create a map to store the patient's appointment data
    val appointmentData = hashMapOf(
        "name" to patientName,
        "email" to patientEmail,
        "problem" to patientProblem,
        "timeSlot" to selectedTimeSlot
    )
    // Retrieve the doctor's email from the arguments
    val doctorEmail = arguments?.getString(ARG_PARAM1)

    // Add the appointment data to Firestore
    db.collection("users")
        .document("Patients")
        .collection("Profile")
        .document(patientEmail).collection("Appointments").document(doctorEmail.toString())
        .set(appointmentData)
        .addOnSuccessListener {
            // Data has been successfully stored in Firestore
            // You can display a success message or navigate to another fragment/activity
        }
        .addOnFailureListener { e ->
            // Handle any errors that may occur during the Firestore operation
            // You can display an error message or log the error
        }
}



    companion object {
        @JvmStatic
        fun newInstance(doctorEmail: String) =
            PatientAppointmentUI().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, doctorEmail) // Store the doctor's email in the arguments
                }
            }
    }


}