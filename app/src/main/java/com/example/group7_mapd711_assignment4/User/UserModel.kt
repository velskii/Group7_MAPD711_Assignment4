package com.example.group7_mapd711_assignment4.User

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

    @ColumnInfo(name = "firstname")
    var firstname: String = ""
    @ColumnInfo(name = "lastname")
    var lastname: String = ""
    @ColumnInfo(name = "address")
    var address: String = ""
    @ColumnInfo(name = "city")
    var city: String = ""
    @ColumnInfo(name = "postalcode")
    var postalcode: String = ""
    @ColumnInfo(name = "telephone")
    var telephone: String = ""
    @ColumnInfo(name = "email")
    var email: String = ""

}