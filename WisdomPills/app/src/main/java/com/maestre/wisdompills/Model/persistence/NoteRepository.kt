package com.maestre.wisdompills.Model.persistence

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.maestre.wisdompills.Model.Note

class NoteRepository {
    private var databaseReference: DatabaseReference
    init {
        databaseReference = FirebaseDatabase.getInstance("https://wisdompills-14-default-rtdb.europe-west1.firebasedatabase.app/").reference
    }

    fun addNote(note: Note) {
        val key = databaseReference.child("notes").push().key
        note.idNote = key
        databaseReference.child("notes").child(key!!).setValue(note)
    }


    fun getNotes(userId: String): MutableLiveData<List<Note>> {
        val notes = MutableLiveData<List<Note>>()

        // Cada vez que los datos cambien, se llamará al evento onDataChange con la nueva lista de datos
        val firebaseDataListener = FirebaseDataListenerNote(notes, userId)
        databaseReference.child("notes").orderByChild("userId").equalTo(userId).addValueEventListener(firebaseDataListener)

        return notes
    }

}