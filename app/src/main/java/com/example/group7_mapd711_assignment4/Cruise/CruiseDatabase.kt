package com.example.group7_mapd711_assignment4.Cruise

/*
  MAPD 711- Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */

import android.content.Context
import androidx.room.*

//Room database
@Database(entities = arrayOf(CruiseModel::class), version = 1, exportSchema = false)
abstract class CruiseDatabase : RoomDatabase() {
    //instantiating Student DAO object
    abstract fun cruiseDao() : CruiseDao

    //companion object means an object declaration inside a class
    companion object {

        //Volatile object or property is immediately made visible to other threads.
        @Volatile
        private var INSTANCE: CruiseDatabase? = null

        // create a database name "CRUISEDB"
        fun getDataseClient(context: Context) : CruiseDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, CruiseDatabase::class.java, "CRUISEDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

                return INSTANCE!!

            }
        }
    }
}