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

    @Query("UPDATE user SET username=:username, password=:password, firstname=:firstname, lastname=:lastname, address=:address, city=:city, postalcode=:postalcode, telephone=:telephone, email=:email WHERE id = :id")
    suspend fun updateUser(id: Int, username: String, password: String, firstname: String, lastname: String, address: String, city: String, postalcode: String, telephone: String, email: String)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}