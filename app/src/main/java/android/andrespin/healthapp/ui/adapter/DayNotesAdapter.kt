package android.andrespin.healthapp.ui.adapter

import android.andrespin.healthapp.databinding.ItemRvNoteDataBinding
import android.andrespin.healthapp.model.NoteData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DayNotesAdapter(private val context: Context): RecyclerView.Adapter<DayNotesViewHolder>() {


    private var data: List<NoteData> = arrayListOf()

    fun setData(data: List<NoteData>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayNotesViewHolder =
        DayNotesViewHolder(
            ItemRvNoteDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )

    override fun onBindViewHolder(holder: DayNotesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}