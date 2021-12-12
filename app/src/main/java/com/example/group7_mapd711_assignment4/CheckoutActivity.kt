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
import com.example.group7_mapd711_assignment4.firebase.Booking
import com.example.group7_mapd711_assignment4.firebase.Cruise

class CheckoutActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id_firebase", "0")
        val bookingId = sharedPreferences.getString("booking_id", "0")

        if (userId != null && bookingId != null) {
            Cruise(userId).getCruiseById(userId).addOnSuccessListener {
                findViewById<TextView>(R.id.cruise_type_checked).text = "Cruise Name: " + it.child("cruiseName").value.toString()
                findViewById<TextView>(R.id.price_stored).text = "Price: " + it.child("price").value.toString() + " $"
                findViewById<TextView>(R.id.visiting_places_stored).text = "Visiting Places: \n" + it.child("visitingPlaces").value.toString()
                findViewById<TextView>(R.id.duration_stored).text = "Duration: " + it.child("duration").value.toString() + " day(s)"
            }
            Booking(userId).getBookingByUidAndBid(userId, bookingId).addOnSuccessListener {
//                Log.e("zz", it.child("startDate").value.toString())
                findViewById<TextView>(R.id.number_adults).text = "Number of adults: \n"+ it.child("numberOfAdults").value.toString()
                findViewById<TextView>(R.id.number_children).text = "Number of children: \n" + it.child("numberOfKids").value.toString()
                findViewById<TextView>(R.id.senior_guest).text = "Number of senior: \n" + it.child("numberOfSeniors").value.toString()
            }
        }
    }

    fun payOptions(v:View){
        val i = Intent(this@CheckoutActivity, PayOptionsActivity::class.java)
        startActivity(i);
    }
}