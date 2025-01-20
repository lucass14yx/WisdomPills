package com.maestre.wisdompills.Model.persistence

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.maestre.wisdompills.Model.Note
import com.maestre.wisdompills.Model.User

class FirebaseDataListenerNote(var dataList: MutableLiveData<List<Note>>) : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        val notes = mutableListOf<Note>()
        for (childSnapshot in snapshot.children) {
            val note = childSnapshot.getValue(Note::class.java)
            if (null != note) {
                notes.add(note)
            }
        }
        dataList.postValue(notes)
    }

    override fun onCancelled(error: DatabaseError) {
        // Handle error, e.g., log or show a message
        Log.e("FirebaseDataListener", "Error: ${error.message}")
    }
}
class FirebaseDataListenerUser(var dataList: MutableLiveData<List<User>>) : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        val users = mutableListOf<User>()
        for (childSnapshot in snapshot.children) {
            val user = childSnapshot.getValue(User::class.java)
            if (null != user) {
                users.add(user)
            }
        }
        dataList.postValue(users)
    }

    override fun onCancelled(error: DatabaseError) {
        // Handle error, e.g., log or show a message
        Log.e("FirebaseDataListener", "Error: ${error.message}")
    }
}