package com.example.group7_mapd711_assignment4.Booking

/*
  MAPD 711- Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.util.*

//provides data to the UI and acts as a communication center
// between the Repository and the UI.
class BookingViewModel : ViewModel() {

    // calling repository tasks and
    // sending the results to the Activity
    var liveDataBooking: LiveData<BookingModel>? = null
    var Bookings: Array<BookingModel>? = null
    var bookingId: Long = 0

    //
    suspend fun insertBooking(context: Context, customerId: Int, cruiseCode: String,
                      numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int, amoutPaid: Double, startDate: String
    ): Long {
        bookingId = BookingRepository.insertBooking(context, customerId, cruiseCode, numberOfAdults, numberOfKids, numberOfSeniors, amoutPaid, startDate)
        return bookingId
    }

    fun getBooking(context: Context, customerId: Int) : LiveData<BookingModel>? {
        liveDataBooking = BookingRepository.getBooking(context, customerId)
        return liveDataBooking
    }

    fun getBookingsByUserId(context: Context, customerId: Int) : Array<BookingModel>? {
        Bookings = BookingRepository.getBookingsByUserId(context, customerId)
        return Bookings
    }


    fun getBookingById(context: Context, id: Int) : LiveData<BookingModel>? {
        liveDataBooking = BookingRepository.getBookingById(context, id)
        return liveDataBooking
    }

    fun updateBooking(context: Context, id: Int, numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int) {
        BookingRepository.updateBooking(context, id, numberOfAdults, numberOfKids, numberOfSeniors)
    }

    fun deleteBookingById(context: Context, id: Int) {
        BookingRepository.deleteBookingById(context,id)
    }
}