package android.andrespin.healthapp.ui.adapter

import android.andrespin.healthapp.databinding.ItemRvNoteDateBinding
import android.andrespin.healthapp.model.DayNotes
import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.NoteData
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DatesViewHolder(
    private val vb: ItemRvNoteDateBinding,
    private val context: Context
) :
    RecyclerView.ViewHolder(vb.root) {

    fun bind(data: DayNotes) {
        // "14 февраля"
        vb.date.text = "${data.date}"


       // vb.date.text = data.date
      //  if (data.notes != null)  initAdapter(data.notes)
    }

    private fun initAdapter(list: List<Note>) {
        val adapter = DayNotesAdapter(context)
        vb.rvData.layoutManager = LinearLayoutManager(context)
        vb.rvData.adapter = adapter
        adapter.setData(list)
    }

}
