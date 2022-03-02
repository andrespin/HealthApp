package android.andrespin.healthapp.ui

import androidx.recyclerview.widget.RecyclerView

class NotesAdapter: RecyclerView.Adapter<NotesViewHolder>() {


}


/*
class DictionaryAdapter : RecyclerView.Adapter<DictionaryViewHolder>() {

    private var data: List<WordDescription> = arrayListOf()

    fun setData(data: List<WordDescription>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder =
        DictionaryViewHolder(
            DictionaryFragmentRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}
 */