package com.example.group7_mapd711_assignment4.Cruise

/*
  MAPD 711- Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

//provides data to the UI and acts as a communication center
// between the Repository and the UI.
class CruiseViewModel : ViewModel() {

    // calling repository tasks and
    // sending the results to the Activity
    var liveDataCruise: LiveData<CruiseModel>? = null
    var cruiseId: Long = 0

    //
    suspend fun insertCruise(context: Context, cruiseCode: String, cruiseName: String, visitingPlaces: String, price: Double, duration: Int): Long {
        cruiseId = CruiseRepository.insertCruise(context, cruiseCode, cruiseName, visitingPlaces, price, duration)
        return cruiseId
    }

    fun getCruise(context: Context, cruiseCode: String) : LiveData<CruiseModel>? {
        liveDataCruise = CruiseRepository.getCruise(context, cruiseCode)
        return liveDataCruise
    }

    fun getCruiseById(context: Context, id: Int) : LiveData<CruiseModel>? {
        liveDataCruise = CruiseRepository.getCruiseById(context, id)
        return liveDataCruise
    }
}