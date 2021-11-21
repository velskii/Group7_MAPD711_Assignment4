package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.Booking.BookingModel
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.Cruise.CruiseViewModel

class BookingListActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var bookingViewModel: BookingViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_list)

        context = this@BookingListActivity
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", 0)
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        val bookingList: Array<BookingModel>? = bookingViewModel.getBookingsByUserId(context, userId)

        var list = emptyArray<String>()
        if (bookingList != null) {
            for (i in bookingList) {
                list += ("BookingId: " + i.BookingId.toString() + " Cruise:" + i.CruiseCode)
            }
        }
        val listView = findViewById<ListView>(R.id.bookings_list_view)

        listView.adapter = ArrayAdapter(this,
            R.layout.list_view_item, list)

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {
                val itemValue = listView.getItemAtPosition(position) as String

                sharedPreferences.edit().putInt(
                    "booking_id_selected", itemValue.substringAfter("BookingId: ").substringBefore("Cruise:").trim().toInt()
                ).apply()

                val i = Intent(this@BookingListActivity, BookingInformationActivity::class.java)
                startActivity(i);
//                Toast.makeText(applicationContext,
//                    "Position :$position\nItem Value : $z", Toast.LENGTH_LONG)
//                    .show()
            }
        }

    }
}