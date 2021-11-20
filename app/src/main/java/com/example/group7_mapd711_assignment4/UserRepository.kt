package com.example.group7_mapd711_assignment4

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UserRepository {

    companion object{
        var userDatabase: UserDatabase? = null
        var userModel: LiveData<UserModel>? = null
        var userId: Long = 0

        fun initializeDB(context: Context) : UserDatabase {
            return UserDatabase.getDatabaseClient(context)
        }

        suspend fun insertUser(context: Context, username: String, password: String): Long {
            userDatabase = initializeDB(context)
//            CoroutineScope(IO).launch {
            val userDetails = UserModel(username, password)
            userId = userDatabase!!.UserDao().insert(userDetails)
//            }
            return userId
        }

        fun getUsersByUsernameAndPassword(context: Context, username: String, password: String) : LiveData<UserModel>? {
            userDatabase = initializeDB(context)
            userModel = userDatabase!!.UserDao().getUsersByUsernameAndPassword(username, password)
            return userModel
        }

        fun getUsersById(context: Context, id: Int) : LiveData<UserModel>? {
            userDatabase = initializeDB(context)
            userModel = userDatabase!!.UserDao().getUsersById(id)
            return userModel
        }
    }
}