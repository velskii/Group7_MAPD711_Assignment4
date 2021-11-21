package com.example.group7_mapd711_assignment4.Cruise

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

//a class for managing multiple data sources
class CruiseRepository {

    //defining database and live data object as companion objects
    companion object {
        var cruiseDatabase: CruiseDatabase? = null
        var cruiseModel: LiveData<CruiseModel>? = null
        var cruiseId: Long = 0

        //initialize database
        fun initializeDB(context: Context) : CruiseDatabase {
            return CruiseDatabase.getDataseClient(context)
        }

        //Initialize insertStudent()
        suspend fun insertCruise(context: Context, cruiseCode: String, cruiseName: String, visitingPlaces: String, price: Double, duration: Int): Long {
            cruiseDatabase = initializeDB(context)

//            CoroutineScope(IO).launch {
                val studentDetails = CruiseModel(cruiseCode, cruiseName, visitingPlaces, price, duration)
                cruiseId = cruiseDatabase!!.cruiseDao().insertCruise(studentDetails)
                return cruiseId
//            }

        }

        fun getCruise(context: Context, cruiseCode: String) : LiveData<CruiseModel>? {
            cruiseDatabase = initializeDB(context)
            cruiseModel = cruiseDatabase!!.cruiseDao().getCruise(cruiseCode)
            return cruiseModel
        }

        fun getCruiseById(context: Context, id: Int) : LiveData<CruiseModel>? {
            cruiseDatabase = initializeDB(context)
            cruiseModel = cruiseDatabase!!.cruiseDao().getCruiseById(id)
            return cruiseModel
        }

    }
}