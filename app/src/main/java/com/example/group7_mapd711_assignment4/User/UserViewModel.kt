package com.example.group7_mapd711_assignment4.User

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    var liveDataUser: LiveData<UserModel>? = null
    var userId: Long = 0

    suspend fun insertUser(context: Context, username: String, password: String): Long {
        userId = UserRepository.insertUser(context, username, password)
        return userId
    }

    fun getUsersByUsernameAndPassword(context: Context, username: String, password: String) : LiveData<UserModel>? {
        liveDataUser = UserRepository.getUsersByUsernameAndPassword(context, username, password)
        return liveDataUser
    }

    fun updateUser(context: Context, id: Int, username: String, password: String, firstname: String, lastname: String, address: String, city: String, postalcode: String, telephone: String, email: String) {
        UserRepository.updateUser(context, id, username, password, firstname, lastname, address, city, postalcode, telephone, email)
    }

    fun getUsersById(context: Context, id: Int) : LiveData<UserModel>? {
        liveDataUser = UserRepository.getUsersById(context, id)
        return liveDataUser
    }

}
