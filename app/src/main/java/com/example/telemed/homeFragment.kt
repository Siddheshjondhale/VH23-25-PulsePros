import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.telemed.HomeFragmenttemp
import com.example.telemed.LoginScreen
import com.example.telemed.R
import com.example.telemed.Search_Doctor
import com.example.telemed.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class homeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailTextView = binding.ProfTitle
        val bookAppointmentButton = binding.bookappoitmentbtn
        val button = binding.logout

        // Check if the user is logged in
        if (currentUser != null) {
            // User is logged in, set their email to the TextView
            val userEmail = currentUser.email
            emailTextView.text = "User Email: $userEmail"

            // Check if the user is a doctor
            val currentUserEmail = currentUser.email ?: ""
            val doctorsCollectionRef = db.collection("users").document("Doctors").collection("profile")

            doctorsCollectionRef.whereEqualTo("email", currentUserEmail)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        // User is not a doctor, show the "Book Appointment" button
                        bookAppointmentButton.visibility = View.VISIBLE
                    } else {
                        // User is a doctor, hide the "Book Appointment" button
                        bookAppointmentButton.visibility = View.GONE
                    }
                }
                .addOnFailureListener { e ->
                    // Handle any errors that occurred during the Firestore query
                    // You can choose to show the "Book Appointment" button by default or handle the error differently
                }
        } else {
            // User is not logged in, handle accordingly (e.g., redirect to login)
            emailTextView.text = "User Email: Not Logged In"
        }

        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        bookAppointmentButton.setOnClickListener {
            val fragmentB = Search_Doctor()
            fragmentTransaction.replace(R.id.mainContainer, fragmentB)
            fragmentTransaction.addToBackStack(null) // Optional: Add the transaction to the back stack
            fragmentTransaction.commit() // Commit the transaction
        }

        button.setOnClickListener {
            // Sign out the current user
            auth.signOut()

            // Optionally, you can navigate the user to the login or home screen
            // For example, if you have a LoginActivity:
            startActivity(Intent(requireContext(), LoginScreen::class.java))
        }


        var btnSos=binding.btnSos

        btnSos.setOnClickListener {
            // Sign out the current user
//            val intent = Intent(requireContext(), FragmentActivityCallSOSBinding::class.java)
//            startActivity(intent)

        }
    }











companion object {
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
        homeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
}



}
