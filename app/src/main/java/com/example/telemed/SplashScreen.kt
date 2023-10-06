package com.example.telemed

import android.content.Intent // Add this import statement
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.os.Handler
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.appcompat.app.AppCompatActivity



class SplashScreen : AppCompatActivity() {

    private val isFirstLaunchKey = "is_first_launch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        installSplashScreen()

        val isFirstLaunch = getSharedPreferences("MyPrefs", MODE_PRIVATE).getBoolean(isFirstLaunchKey, true)

        Handler(mainLooper).postDelayed({
            if (isFirstLaunch) {
                // It's the first launch, navigate to EcoStart activity
                startActivity(Intent(this, LoginScreen::class.java))

                // Set the first launch flag to false
                getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().putBoolean(isFirstLaunchKey, false).apply()
            } else {
                // Not the first launch, navigate to MainActivity or any other appropriate activity
                startActivity(Intent(this, LoginScreen::class.java))
            }

            finish()
        }, 500)
    }
}





//class SplashScreen : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash_screen)
//        installSplashScreen()
//        Handler(mainLooper).postDelayed({
//            // Check if the user is already authenticated
////            val firebaseAuth = FirebaseAuth.getInstance()
////            val currentUser = firebaseAuth.currentUser
////
////            if (currentUser != null) {
////                // User is already logged in, navigate to MainActivity
////                startActivity(Intent(this, MainActivity::class.java))
////            } else {
////                // User is not logged in, navigate to LoginActivity
////                startActivity(Intent(this, MainActivity::class.java))
////            }
//                startActivity(Intent(this, MainActivity::class.java))
//
//            finish()
//        }, 500)
//
//    }
//}

