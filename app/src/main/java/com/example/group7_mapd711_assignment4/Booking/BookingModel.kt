package com.example.group7_mapd711_assignment4.Booking

/*
  MAPD 711- Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

//Room entity data class - model
//This class describes a database table
@Entity(tableName = "booking")
data class BookingModel(
    //defining a column CruiseCode
    @ColumnInfo(name = "customerId")
    var CustomerId: Int,
    //defining a column CruiseName
    @ColumnInfo(name = "cruiseCode")
    var CruiseCode: String,
    //defining a column visitingPlaces
    @ColumnInfo(name = "numberOfAdults")
    var NumberOfAdults: Int,
    //defining a column Price
    @ColumnInfo(name = "numberOfKids")
    var NumberOfKids: Int,
    //defining a column Duration
    @ColumnInfo(name = "numberOfSeniors")
    var NumberOfSeniors: Int,
    //defining a column Duration
    @ColumnInfo(name = "amountPaid")
    var AmountPaid: Double,
    //defining a column Duration
    @ColumnInfo(name = "startDate")
    var StartDate: String
)
{
    //defining a primary key field Id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookingId")
    var BookingId: Int? = null
}

