package com.maestre.wisdompills.Model.persistence

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.maestre.wisdompills.Model.User

class UserRepository {
    private var databaseReference: DatabaseReference
    init {
        databaseReference = FirebaseDatabase.getInstance("https://wisdompills-14-default-rtdb.europe-west1.firebasedatabase.app/").reference
    }

    fun addUser(user: User) {
        val key = databaseReference.child("users").push().key
        user.idUser = key
        databaseReference.child("users").child(key!!).setValue(user)
    }

    fun getUsers(): MutableLiveData<List<User>> {
        val users = MutableLiveData<List<User>>()

        // Cada vez que los datos cambien, se llamará al evento onDataChange con la nueva lista de datos
        val firebaseDataListener = FirebaseDataListenerUser(users)
        databaseReference.child("users").addValueEventListener(firebaseDataListener)

        return users
    }
}