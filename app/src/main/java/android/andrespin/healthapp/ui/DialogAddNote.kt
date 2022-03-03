package android.andrespin.healthapp.ui

import android.andrespin.healthapp.databinding.DialogAddNoteBinding
import android.andrespin.healthapp.model.NoteData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

open class DialogAddNote : DialogFragment() {

    private lateinit var binding: DialogAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DialogAddNoteBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        with(binding) {

            imgCancel.setOnClickListener {
                findNavController().popBackStack()
            }

            imgSave.setOnClickListener {

                val date = editTextDate.text.toString()
                val time = editTextTime.text.toString()
                val upperPressure = editTextUpperPressure.text.toString()
                val lowerPressure = editTextLowerPressure.text.toString()
                val pulse = editTextPulse.text.toString()

                val navController = findNavController()
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "key",
                    NoteData(
                        date,
                        time,
                        upperPressure,
                        lowerPressure,
                        pulse
                    )
                )
                navController.popBackStack()
            }
        }
    }


}