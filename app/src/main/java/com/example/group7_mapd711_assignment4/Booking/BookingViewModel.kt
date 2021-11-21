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

    //
    fun insertBooking(context: Context, customerId: String, cruiseCode: String,
                      numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int, amoutPaid: Double, startDate: String
    ) {
        BookingRepository.insertBooking(context, customerId, cruiseCode, numberOfAdults, numberOfKids, numberOfSeniors, amoutPaid, startDate)
    }

    fun getBooking(context: Context, customerId: String) : LiveData<BookingModel>? {
        liveDataBooking = BookingRepository.getBooking(context, customerId)
        return liveDataBooking
    }
}