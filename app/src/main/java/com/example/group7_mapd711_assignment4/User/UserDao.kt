package com.example.group7_mapd711_assignment4.User

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user where username =:username and password = :password")
    fun getUsersByUsernameAndPassword(username: String?, password: String?): LiveData<UserModel>

    @Query("SELECT * FROM user where id =:id")
    fun getUsersById(id: Int?): LiveData<UserModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userModel: UserModel) : Long

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}