package com.example.telemed

data class DoctorAppointment(
    val patientName: String,
    val problem: String,
    val timeSlot: String
)