package android.andrespin.healthapp.ui

import android.andrespin.healthapp.databinding.ItemRvNoteDateBinding
import android.andrespin.healthapp.model.DayNotes
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DatesAdapter(private val context: Context): RecyclerView.Adapter<DatesViewHolder>() {

    private var data: List<DayNotes> = arrayListOf()

    fun setData(data: List<DayNotes>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder =
        DatesViewHolder(
            ItemRvNoteDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}


