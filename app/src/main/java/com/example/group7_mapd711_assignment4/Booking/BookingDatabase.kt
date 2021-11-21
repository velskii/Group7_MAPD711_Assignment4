package com.example.group7_mapd711_assignment4.Booking

/*
  MAPD 711- Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */

import android.content.Context
import androidx.room.*

//Room database
@Database(entities = arrayOf(BookingModel::class), version = 1, exportSchema = false)
abstract class BookingDatabase : RoomDatabase() {
    //instantiating Student DAO object
    abstract fun bookingDao() : BookingDao

    //companion object means an object declaration inside a class
    companion object {

        //Volatile object or property is immediately made visible to other threads.
        @Volatile
        private var INSTANCE: BookingDatabase? = null

        // create a database name "CRUISEDB"
        fun getDataseClient(context: Context) : BookingDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, BookingDatabase::class.java, "BOOKINGDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

                return INSTANCE!!

            }
        }
    }
}