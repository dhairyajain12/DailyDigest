package com.dailydigest.interfaces

import android.view.View
import com.dailydigest.models.NotesHomeModel

interface NoteOptionClickListener {
    fun noteOptionMenuClicked(note: NotesHomeModel, view: View)
    fun noteSelectionClicked(note: NotesHomeModel)
}
