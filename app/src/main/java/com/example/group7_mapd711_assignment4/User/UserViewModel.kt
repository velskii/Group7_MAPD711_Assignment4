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

    fun getUsersById(context: Context, id: Int) : LiveData<UserModel>? {
        liveDataUser = UserRepository.getUsersById(context, id)
        return liveDataUser
    }

}
