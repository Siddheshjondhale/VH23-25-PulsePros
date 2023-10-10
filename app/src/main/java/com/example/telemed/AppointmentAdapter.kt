import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.telemed.Appointment
import com.example.telemed.R

class AppointmentAdapter(private val appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textName)
        val emaildId: TextView = itemView.findViewById(R.id.textEmail)
        val problemTextView: TextView = itemView.findViewById(R.id.textProblem)
        val slotTimeTextView: TextView = itemView.findViewById(R.id.textSlotTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAppointment = appointments[position]

        // Bind data to the TextViews in the item view
        holder.nameTextView.text = currentAppointment.name
        holder.emaildId.text = currentAppointment.email
        holder.problemTextView.text = currentAppointment.problem
        holder.slotTimeTextView.text = currentAppointment.slotTime
    }

    override fun getItemCount() = appointments.size
}
