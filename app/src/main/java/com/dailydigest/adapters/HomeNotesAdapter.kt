package com.dailydigest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.dailydigest.R
import com.dailydigest.interfaces.NoteOptionClickListener
import com.dailydigest.models.NotesHomeModel

class HomeNotesAdapter(private val notes: ArrayList<NotesHomeModel>, val noteOptionsListener: NoteOptionClickListener, private val itemClicked: (NotesHomeModel) -> Unit):
    Adapter<HomeNotesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View, val itemClicked: (NotesHomeModel) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val noteTitle: TextView = itemView.findViewById(R.id.noteTitleHome)
        private val noteDesc: TextView = itemView.findViewById(R.id.noteDescriptionHome)
        private val cardView: CardView = itemView.findViewById(R.id.notecardview)

        fun getNote (note: NotesHomeModel) {
            noteTitle.text = note.noteTitle
            noteDesc.text = note.noteDesc

//            val dateFormatter = SimpleDateFormat("dd, h:mm a", Locale.getDefault())
//            val date = dateFormatter.format(Note.timeStamp.toDate()).toString()
//            timeStamp.text = date
            itemView.setOnClickListener { itemClicked(note) }

            cardView.setOnLongClickListener {
                noteOptionsListener.noteOptionMenuClicked(note, cardView)
                true
            }
            cardView.setOnClickListener {
                noteOptionsListener.noteSelectionClicked(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val notes = LayoutInflater.from(parent.context).inflate(R.layout.notes_rv_cell, parent, false)
        return ViewHolder(notes, itemClicked)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getNote(notes[position])
    }
}
