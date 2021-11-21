package com.example.group7_mapd711_assignment4.Booking

/*
  MAPD 711- Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

//a class for managing multiple data sources
class BookingRepository {

    //defining database and live data object as companion objects
    companion object {
        var bookingDatabase: BookingDatabase? = null
        var bookingModel: LiveData<BookingModel>? = null

        //initialize database
        fun initializeDB(context: Context) : BookingDatabase {
            return BookingDatabase.getDataseClient(context)
        }

        //Initialize insertStudent()
        fun insertBooking(context: Context, customerId: Int, cruiseCode: String,
                          numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int, amoutPaid: Double, startDate: String) {
            bookingDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                val studentDetails = BookingModel(customerId, cruiseCode, numberOfAdults, numberOfKids, numberOfSeniors, amoutPaid, startDate)
                bookingDatabase!!.bookingDao().insertBooking(studentDetails)
            }

        }

        //Initialize getStudents()
        fun getBooking(context: Context, customerId: Int) : LiveData<BookingModel>? {
            bookingDatabase = initializeDB(context)
            bookingModel = bookingDatabase!!.bookingDao().getBooking(customerId)
            return bookingModel
        }

    }
}