/*
  MAPD 711 - Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
package com.example.group7_mapd711_assignment4

import android.annotation.SuppressLint
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel

class HomeActivity : AppCompatActivity() {

    lateinit var bookingViewModel: BookingViewModel
    lateinit var context: Context
    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val linkPersonalInfo: Button = findViewById<View>(R.id.btnPersonalInfo) as Button
        linkPersonalInfo.setOnClickListener{
            val i = Intent(this@HomeActivity, UserInformationActivity::class.java)
            startActivity(i)
        }

        val linkBooking: Button = findViewById<View>(R.id.btnBooking) as Button
        linkBooking.setOnClickListener{
            val i = Intent(this@HomeActivity, CruiseTypesActivity::class.java)
            startActivity(i);
        }
        context = this@HomeActivity
        val bookingInformation: Button = findViewById<View>(R.id.btnBookingInfo) as Button
        bookingInformation.setOnClickListener{
//            val i = Intent(this@HomeActivity, BookingInformationActivity::class.java)
            val i = Intent(this@HomeActivity, BookingListActivity::class.java)
            startActivity(i);
        }

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "no name")

        findViewById<TextView>(R.id.welcome).text = "Welcome $username! Please enjoy your trip."
    }
}