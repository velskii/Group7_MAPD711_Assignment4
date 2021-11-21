package com.example.group7_mapd711_assignment4.Booking

/*
  MAPD 711- Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Room DAO - Data Access Object Interface
// this interface declares database functions
// and does the mapping of SQL queries to functions
@Dao
interface BookingDao {

    //defining an insert method using @Insert Annotation
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(bookingModel: BookingModel): Long

    //defining a query method using @Query Annotation
    @Query("SELECT * FROM booking WHERE customerId =:customerId")
    fun getBooking(customerId: Int) : LiveData<BookingModel>

    @Query("SELECT * FROM booking WHERE bookingId =:id")
    fun getBookingById(id: Int) : LiveData<BookingModel>

    //defining an update method
    @Query("UPDATE booking SET numberOfAdults=:numberOfAdults, numberOfKids=:numberOfKids, numberOfSeniors=:numberOfSeniors WHERE customerId = :id")
    suspend fun updateBooking(id: Int, numberOfAdults: Int, numberOfKids: Int, numberOfSeniors: Int)
}