/*
  MAPD 711 - Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
package com.example.group7_mapd711_assignment4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.Cruise.CruiseViewModel
import java.util.Observer

class CheckoutActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var cruiseViewModel : CruiseViewModel
    lateinit var bookingViewModel : BookingViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        cruiseViewModel = ViewModelProvider(this).get(CruiseViewModel::class.java)
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)

        val cruiseId = sharedPreferences.getLong("cruise_id", 0)
        cruiseViewModel.getCruiseById(this@CheckoutActivity, cruiseId.toInt())?.observe(this, {
            val code = it.CruiseCode
            val cruiseName = it.CruiseName
            val duration = it.Duration
            val price = it.Price
            val visitingPlaces = it.VisitingPlaces

            findViewById<TextView>(R.id.cruise_type_checked).text = cruiseName
            findViewById<TextView>(R.id.price_stored).text = price.toString()
            findViewById<TextView>(R.id.visiting_places_stored).text = visitingPlaces
            findViewById<TextView>(R.id.duration_stored).text = duration.toString()

        })

        val booking_id = sharedPreferences.getLong("booking_id", 0)

        bookingViewModel.getBookingById(this@CheckoutActivity, booking_id.toInt())?.observe(this, {

            findViewById<TextView>(R.id.number_adults).text = "Number of adults: "+ it.NumberOfAdults.toString()
            findViewById<TextView>(R.id.number_children).text = "Number of children: " + it.NumberOfKids.toString()
            findViewById<TextView>(R.id.senior_guest).text = "Number of senior: " + it.NumberOfSeniors.toString()

        })
//        Toast.makeText( this@CheckoutActivity,"info:${it.CruiseCode}", Toast.LENGTH_SHORT).show()


    }

    fun payOptions(v:View){
        val i = Intent(this@CheckoutActivity, PayOptionsActivity::class.java)
        startActivity(i);
    }
}