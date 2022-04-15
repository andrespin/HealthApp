package android.andrespin.healthapp.ui.adapter

import android.andrespin.healthapp.databinding.ItemRvNoteDataBinding
import android.andrespin.healthapp.model.Note
import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class DayNotesViewHolder(private val vb: ItemRvNoteDataBinding, private val context: Context) :
    RecyclerView.ViewHolder(vb.root) {

    fun bind(note: Note) {
        vb.txtTime.text = note.time
        vb.txtLowerPressure.text = note.lowerPressure.toString()
        vb.txtUpperPressure.text = note.upperPressure.toString()
        vb.txtPulse.text = note.pulse.toString()

        vb.backNote.background = AppCompatResources.getDrawable(
            context,
            note.background!!
        )
    }

}