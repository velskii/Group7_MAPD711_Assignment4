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
        var bookingId: Long = 0

        //initialize database
        fun initializeDB(context: Context) : BookingDatabase {
            return BookingDatabase.getDataseClient(context)
        }

        //Initialize insertStudent()
        suspend fun insertBooking(context: Context, customerId: Int, cruiseCode: String,
                          numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int, amoutPaid: Double, startDate: String): Long {
            bookingDatabase = initializeDB(context)

//            CoroutineScope(IO).launch {
                val studentDetails = BookingModel(customerId, cruiseCode, numberOfAdults, numberOfKids, numberOfSeniors, amoutPaid, startDate)
                bookingId = bookingDatabase!!.bookingDao().insertBooking(studentDetails)
                return bookingId
//            }

        }

        //Initialize getStudents()
        fun getBooking(context: Context, customerId: Int) : LiveData<BookingModel>? {
            bookingDatabase = initializeDB(context)
            bookingModel = bookingDatabase!!.bookingDao().getBooking(customerId)
            return bookingModel
        }

        fun updateBooking(context: Context, id: Int, numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int) {
            bookingDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
//                val userDetails = UserModel(username, password, firstname, lastname, address, city, postalcode, telephone, email)
                bookingDatabase!!.bookingDao().updateBooking(id, numberOfAdults, numberOfKids, numberOfSeniors)
            }
        }
    }
}