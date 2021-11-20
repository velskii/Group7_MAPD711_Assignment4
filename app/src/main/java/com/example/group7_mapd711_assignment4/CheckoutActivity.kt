package com.example.group7_mapd711_assignment4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class CheckoutActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment2", Context.MODE_PRIVATE)

        val cruise_type_checked = sharedPreferences.getString("cruise_type_checked", "").toString()
        val price = sharedPreferences.getString("price", "").toString()
        val visiting_places = sharedPreferences.getString("visiting_places", "").toString()
        val duration = sharedPreferences.getString("duration", "").toString()
        val numberOfAdults = sharedPreferences.getString("numberOfAdults", "").toString()
        val numberOfChildren = sharedPreferences.getString("numberOfChildren", "").toString()
        val senior_guest = sharedPreferences.getString("senior_guest", "NO").toString()

        findViewById<TextView>(R.id.cruise_type_checked).text = cruise_type_checked
        findViewById<TextView>(R.id.price_stored).text = price
        findViewById<TextView>(R.id.visiting_places_stored).text = visiting_places
        findViewById<TextView>(R.id.duration_stored).text = duration
        findViewById<TextView>(R.id.number_adults).text = "Number of adults: $numberOfAdults"
        findViewById<TextView>(R.id.number_children).text = "Number of children: $numberOfChildren"
        findViewById<TextView>(R.id.senior_guest).text = "Anyone over the age of 60: $senior_guest"
    }

    fun payOptions(v:View){
        val i = Intent(this@CheckoutActivity, PayOptionsActivity::class.java)
        startActivity(i);
    }
}