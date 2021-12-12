package com.example.group7_mapd711_assignment4.firebase

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Exclude
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class User(val id: String)
{
    private val database = Firebase.database
    var username: String = ""
    var password: String = ""
    var firstname: String = ""
    var lastname: String = ""
    var address: String = ""
    var city: String = ""
    var postalcode: String = ""
    var telephone: String = ""
    var email: String = ""

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "username" to username,
            "password" to password,
            "firstname" to firstname,
            "lastname" to lastname,
            "address" to address,
            "city" to city,
            "postalcode" to postalcode,
            "telephone" to telephone,
            "email" to email,
        )
    }

    fun getUserByUid(uid: String): Task<DataSnapshot> {
        val userTable = database.getReference("userTable")

        return userTable.child(uid).get()
    }

    public fun writeNewUser( username: String, password: String, firstname: String, lastname: String, address: String, city: String, postalcode: String, telephone: String, email: String) {

        val userTable = database.getReference("userTable")

        val key = userTable.push().key
        if (key == null) {
            Log.w("UserModel", "Couldn't get push key for users")
            return
        }

        val newUser = User(id)
        newUser.username = username
        newUser.password = password
        newUser.firstname = firstname
        newUser.lastname = lastname
        newUser.address = address
        newUser.city = city
        newUser.postalcode = postalcode
        newUser.telephone = telephone
        newUser.email = email
        val values = newUser.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/$id" to values,
//            "/users/$key" to values,
        )
        userTable.updateChildren(childUpdates)
    }

}