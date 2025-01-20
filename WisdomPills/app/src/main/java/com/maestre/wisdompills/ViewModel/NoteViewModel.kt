package com.maestre.wisdompills.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.maestre.wisdompills.Model.Note
import com.maestre.wisdompills.Model.persistence.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    lateinit var notesLiveData: LiveData<List<Note>>

    init {
        repository = NoteRepository()
        getNotes()
    }

    fun addNote(titulo: String, contenido: String) {
        val note = Note(null, titulo, contenido)
        repository.addNote(note)
    }

    private fun getNotes() {
        notesLiveData= repository.getNotes()
    }
}