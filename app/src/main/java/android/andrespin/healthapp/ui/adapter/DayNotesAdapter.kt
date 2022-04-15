package android.andrespin.healthapp.ui.adapter

import android.andrespin.healthapp.MainIntent
import android.andrespin.healthapp.R
import android.andrespin.healthapp.databinding.ItemRvNoteDataBinding
import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.NoteData
import android.andrespin.healthapp.ui.MainFragment
import android.andrespin.healthapp.viewmodel.MainViewModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DayNotesAdapter(
    private val context: Context,
    private val mainFragment: MainFragment,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<DayNotesViewHolder>() {


    private var data: List<Note> = arrayListOf()

    fun setData(data: List<Note>) {
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
        ).apply {
            itemView.setOnClickListener {

            }
            itemView.setOnLongClickListener {
                val pop = PopupMenu(itemView.context, it)
                pop.inflate(R.menu.note_menu)
                pop.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.noteMenuDelete -> {
                            val d = data[this.layoutPosition]
                            mainFragment.lifecycleScope.launch {
                                viewModel.intent.send(MainIntent.DeleteNote(d))
                            }
                        }

                        R.id.noteMenuCancel -> {

                        }
                    }
                    true
                }
                pop.show()
                println("Long click")
                true
            }
        }

    override fun onBindViewHolder(holder: DayNotesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}