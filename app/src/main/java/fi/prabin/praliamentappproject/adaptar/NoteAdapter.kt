package fi.prabin.praliamentappproject.adaptar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.database.Note

class NotesAdapter(val context: Context,
                   val noteClickDeleteInterface: NoteClickDeleteInterface,
                   val allNotes : List<Note>
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

    class NotesViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.notes_view,
            parent, false
        )
        return NotesViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.noteTV.text = allNotes[position].note
        holder.dateTV.text = allNotes[position].timeStamp

        // implement setOnClickListener event on note item.
        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }

    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

}

interface NoteClickDeleteInterface {
    fun onDeleteIconClick(note: Note)
}