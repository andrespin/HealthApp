package android.andrespin.healthapp.ui.adapter

import android.andrespin.healthapp.databinding.ItemRvNoteDateBinding
import android.andrespin.healthapp.model.DayNotes
import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.NoteData
import android.andrespin.healthapp.ui.MainFragment
import android.andrespin.healthapp.viewmodel.MainViewModel
import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DatesViewHolder(
    private val vb: ItemRvNoteDateBinding,
    private val context: Context,
    private val mainFragment: MainFragment,
    private val viewModel: MainViewModel
) :
    RecyclerView.ViewHolder(vb.root) {

    @SuppressLint("SetTextI18n")
    fun bind(data: DayNotes) {
        val number = data.date.number
        val mounth = data.date.mounth
        val year = data.date.year
        vb.date.text = "$number.$mounth.$year"
        initAdapter(data.notes)
    }

    private fun initAdapter(list: List<Note>) {
        val adapter = DayNotesAdapter(context, mainFragment, viewModel)
        vb.rvData.layoutManager = LinearLayoutManager(context)
        vb.rvData.adapter = adapter
        adapter.setData(list)
    }

}
