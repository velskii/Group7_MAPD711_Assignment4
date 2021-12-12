package com.example.group7_mapd711_assignment4.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Exclude
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Booking (var uid: String) {
    private  val database = Firebase.database
    var id: String = ""
    var cruiseCode: String = ""
    var numberOfAdults: Int = 0
    var numberOfKids: Int = 0
    var numberOfSeniors: Int = 0
    var amountPaid: String = ""
    var startDate: String = ""


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "cruiseCode" to cruiseCode,
            "numberOfAdults" to numberOfAdults,
            "numberOfKids" to numberOfKids,
            "numberOfSeniors" to numberOfSeniors,
            "amountPaid" to amountPaid,
            "startDate" to startDate,
        )
    }

    fun getBookingByUid(uid: String): Task<DataSnapshot> {
        val bookingTable = database.getReference("bookingTable")

        return bookingTable.child(uid).get()
    }

    fun getBookingByUidAndBid(uid: String, bookingId: String): Task<DataSnapshot> {
        val bookingTable = database.getReference("bookingTable")

        return bookingTable.child(uid).child(bookingId).get()
    }

    fun deleteByUidAndBid(uid: String, bookingId: String) {
        val bookingTable = database.getReference("bookingTable")

        bookingTable.child(uid).child(bookingId).removeValue()
    }

    fun updateBooking(id: String = "", cruiseCode: String, numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int, amountPaid: String, startDate: String): String? {
        val bookingTable = database.getReference("bookingTable")
        val newBooking = Booking(uid)

        newBooking.id = id
        if (cruiseCode != "") {
            newBooking.cruiseCode = cruiseCode
        }
        if (numberOfAdults != 0) {
            newBooking.numberOfAdults = numberOfAdults
        }
        if (numberOfKids != 0) {
            newBooking.numberOfKids = numberOfKids
        }
        if (numberOfSeniors != 0) {
            newBooking.numberOfSeniors = numberOfSeniors
        }
        if (amountPaid != "") {
            newBooking.amountPaid = amountPaid
        }
        if (startDate != "") {
            newBooking.startDate = startDate
        }

        val values = newBooking.toMap()

        bookingTable.updateChildren(hashMapOf<String, Any>(
            "/$uid/$id" to values,
        ))
        return id
    }

    fun createBooking(id: String = "", cruiseCode: String, numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int, amountPaid: String, startDate: String): String? {
        val bookingTable = database.getReference("bookingTable")
        val key = bookingTable.push().key
        if (key == null) {
            return "error: Couldn't get push key for bookings"
        }
        val newBooking = Booking(uid)
        if (id == "") {
            newBooking.id = key
        } else {
            newBooking.id = id
        }
        newBooking.cruiseCode = cruiseCode
        newBooking.numberOfAdults = numberOfAdults
        newBooking.numberOfKids = numberOfKids
        newBooking.numberOfSeniors = numberOfSeniors
        newBooking.amountPaid = amountPaid
        newBooking.startDate = startDate
        val values = newBooking.toMap()

        bookingTable.updateChildren(hashMapOf<String, Any>(
            "/$uid/$key" to values,
        ))
        return key
    }
}