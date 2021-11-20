package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SummaryActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment2", Context.MODE_PRIVATE)

        val cruise_type_checked = sharedPreferences.getString("cruise_type_checked", "").toString()
        val city_and_country = sharedPreferences.getString("city", "").toString() + "-" + sharedPreferences.getString("country", "").toString()
        val customer_address = sharedPreferences.getString("address", "").toString()
        val customer_name = sharedPreferences.getString("full_name", "").toString()

        val price = sharedPreferences.getString("price", "").toString()
        val visiting_places = sharedPreferences.getString("visiting_places", "").toString()
        val duration = sharedPreferences.getString("duration", "").toString()
        val numberOfAdults = ((sharedPreferences.getString("numberOfChildren", "")?.toInt() ?: 0) + (sharedPreferences.getString("numberOfAdults", "")
            ?.toInt() ?: 0)).toString()

        findViewById<TextView>(R.id.type_of_cruise).text = cruise_type_checked
        findViewById<TextView>(R.id.customer_name).text = customer_name
        findViewById<TextView>(R.id.customer_address).text = customer_address
        findViewById<TextView>(R.id.city_and_country).text = city_and_country
        findViewById<TextView>(R.id.cruise_duration_reverse).text = duration
        findViewById<TextView>(R.id.price_reverse).text = price
        findViewById<TextView>(R.id.number_of_nights).text = duration
        findViewById<TextView>(R.id.visiting_places_reverse).text = visiting_places
        findViewById<TextView>(R.id.number_of_guests).text = numberOfAdults
    }
}