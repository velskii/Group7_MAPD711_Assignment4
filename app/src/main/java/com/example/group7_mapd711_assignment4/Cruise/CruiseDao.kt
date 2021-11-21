package com.example.group7_mapd711_assignment4.Cruise

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
interface CruiseDao {

    //defining an insert method using @Insert Annotation
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCruise(cruiseModel: CruiseModel): Long

    //defining a query method using @Query Annotation
    @Query("SELECT * FROM cruise WHERE CruiseName =:cruiseName")
    fun getCruise(cruiseName: String?) : LiveData<CruiseModel>

    @Query("SELECT * FROM cruise WHERE id =:id")
    fun getCruiseById(id: Int?) : LiveData<CruiseModel>

}