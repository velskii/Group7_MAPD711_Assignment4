package com.example.group7_mapd711_assignment4.Cruise

/*
  MAPD 711- Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Room entity data class - model
//This class describes a database table
@Entity(tableName = "cruise")
data class CruiseModel(
    //defining a column CruiseCode
    @ColumnInfo(name = "cruiseCode")
    var CruiseCode: String,
    //defining a column CruiseName
    @ColumnInfo(name = "cruiseName")
    var CruiseName: String,
    //defining a column visitingPlaces
    @ColumnInfo(name = "visitingPlaces")
    var VisitingPlaces: String,
    //defining a column Price
    @ColumnInfo(name = "price")
    var Price: Double,
    //defining a column Duration
    @ColumnInfo(name = "duration")
    var Duration: Int
)
{
    //defining a primary key field Id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}

