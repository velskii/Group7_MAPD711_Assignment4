package com.example.group7_mapd711_assignment4

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password")
    var password: String,
)
{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

}