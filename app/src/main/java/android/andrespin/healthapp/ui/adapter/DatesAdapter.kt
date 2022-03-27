package android.andrespin.healthapp.ui.adapter

import android.andrespin.healthapp.MainIntent
import android.andrespin.healthapp.R
import android.andrespin.healthapp.databinding.ItemRvNoteDateBinding
import android.andrespin.healthapp.model.DayNotes
import android.andrespin.healthapp.ui.MainFragment
import android.andrespin.healthapp.viewmodel.MainViewModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DatesAdapter(
    private val context: Context,
    private val mainFragment: MainFragment,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<DatesViewHolder>() {

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
            context,
            mainFragment,
            viewModel
        ).apply {
            itemView.setOnClickListener {

            }
            itemView.setOnLongClickListener {
                val pop = PopupMenu(itemView.context, it)
                pop.inflate(R.menu.day_notes_menu)
                pop.setOnMenuItemClickListener { item ->

                    when (item.itemId) {
                        R.id.dayMenuDelete -> {
                            println("this.layoutPosition " + this.layoutPosition)
                            println("data[this.layoutPosition] " + data[this.layoutPosition])
                            val d = data[this.layoutPosition]
                            mainFragment.lifecycleScope.launch{
                                viewModel.intent.send(MainIntent.DeleteDayNotes(d))
                            }
                        }

                        R.id.dayMenuCancel -> {

                        }
                    }
                    true
                }
                pop.show()
                println("Long click")
                true
            }
        }

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}


