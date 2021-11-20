package com.example.group7_mapd711_assignment4.User

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
        fun updateUser(context: Context, id: Int, username: String, password: String, firstname: String, lastname: String, address: String, city: String, postalcode: String, telephone: String, email: String) {
            userDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
//                val userDetails = UserModel(username, password, firstname, lastname, address, city, postalcode, telephone, email)
                userDatabase!!.UserDao().updateUser(id, username, password, firstname, lastname, address, city, postalcode, telephone, email)
            }
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