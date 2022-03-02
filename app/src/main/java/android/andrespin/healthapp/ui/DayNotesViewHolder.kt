package android.andrespin.healthapp.ui

import android.andrespin.healthapp.R
import android.andrespin.healthapp.databinding.ItemRvNoteDataBinding
import android.andrespin.healthapp.model.NoteData
import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView


class DayNotesViewHolder(private val vb: ItemRvNoteDataBinding, private val context: Context) :
    RecyclerView.ViewHolder(vb.root){

    fun bind(noteData: NoteData) {
        vb.txtTime.text = noteData.time
        vb.txtLowerPressure.text = noteData.lowerPressure.toString()
        vb.txtUpperPressure.text = noteData.upperPressure.toString()
        vb.txtPulse.text = noteData.pulse.toString()
        vb.backNote.background = AppCompatResources.getDrawable(
            context,
            noteData.background ?: R.color.white
        )
    }

}