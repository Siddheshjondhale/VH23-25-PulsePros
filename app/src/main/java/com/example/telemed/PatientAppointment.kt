package com.example.telemed

data class PatientAppointment(
    val name: String,
    val email: String,
    val problem: String,
    val timeSlot: String
)