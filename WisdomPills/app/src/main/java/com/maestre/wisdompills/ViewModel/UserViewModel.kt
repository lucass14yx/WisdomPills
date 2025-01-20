package com.maestre.wisdompills.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.maestre.wisdompills.Model.User
import com.maestre.wisdompills.Model.persistence.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    lateinit var usersLiveData: LiveData<List<User>>

    init {
        repository = UserRepository()
        getUsers()
    }

    fun addUser(email: String, password: String, nickname: String) {
        val user = User(null, nickname, password, email)
        repository.addUser(user)
    }

    private fun getUsers() {
        usersLiveData= repository.getUsers()
    }
}