package fi.prabin.praliamentappproject.adaptar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import fi.prabin.praliamentappproject.R

class PartyListAdapter(private val dataset: List<String>) : RecyclerView.Adapter<PartyListAdapter.ParliamentViewHolder>() {

    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    class ParliamentViewHolder(val view : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button_item)

        init {
            view.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParliamentViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_list_view, parent,false)
        return ParliamentViewHolder(layout, mListener)
    }

    override fun onBindViewHolder(holder: ParliamentViewHolder, position: Int) {
        val item = dataset[position]
        holder.button.text = item
    }

    override fun getItemCount() = dataset.size
}