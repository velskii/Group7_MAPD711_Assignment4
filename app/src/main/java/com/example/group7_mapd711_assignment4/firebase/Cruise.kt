package com.example.group7_mapd711_assignment4.firebase

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Exclude
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Cruise (var uid: String)
{
    private  val database = Firebase.database
    var id: String = ""
    var cruiseCode: String = ""
    var cruiseName: String = ""
    var visitingPlaces: String = ""
    var price: String = ""
    var duration: String = ""


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "cruiseCode" to cruiseCode,
            "cruiseName" to cruiseName,
            "visitingPlaces" to visitingPlaces,
            "price" to price,
            "duration" to duration,
        )
    }

    fun getCruiseById(uid: String): Task<DataSnapshot> {
        val cruiseTable = database.getReference("cruiseTable")

        return cruiseTable.child(uid).get()
    }

    fun updateCruise( cruiseCode: String, cruiseName: String, visitingPlaces: String, price: String, duration: String): String? {
        val cruiseTable = database.getReference("cruiseTable")
        val key = cruiseTable.push().key
        if (key == null) {
//            Log.w("CruiseModel", "Couldn't get push key for cruises")
            return "error: Couldn't get push key for cruises"
        }

        val newCruise = Cruise(uid)
        newCruise.id = key
        newCruise.cruiseCode = cruiseCode
        newCruise.cruiseName = cruiseName
        newCruise.visitingPlaces = visitingPlaces
        newCruise.price = price
        newCruise.duration = duration
        val values = newCruise.toMap()

        cruiseTable.updateChildren(hashMapOf<String, Any>(
            "/$uid" to values,
        ))
        return key
    }
}