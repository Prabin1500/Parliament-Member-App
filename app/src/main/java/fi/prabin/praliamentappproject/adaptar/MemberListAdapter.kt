package fi.prabin.praliamentappproject.adaptar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.database.ParliamentMemberInfo

class MemberListAdapter(private val dataset: List<ParliamentMemberInfo>) :
    RecyclerView.Adapter<MemberListAdapter.ParliamentViewHolder>() {

    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    class ParliamentViewHolder(view : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val textView : Button = view.findViewById(R.id.button_item)

        init {
            view.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParliamentViewHolder {
        // inflating our layout file for each item of recycler view.
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_list_view, parent,false)
        return ParliamentViewHolder(layout, mListener)
    }

    override fun onBindViewHolder(holder: ParliamentViewHolder, position: Int) {
        val item = dataset[position]
        val firstName = item.firstname
        val lastName = item.lastname
        val name = "$firstName $lastName"
        holder.textView.text = name
    }

    override fun getItemCount() = dataset.size
}