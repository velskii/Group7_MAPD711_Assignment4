package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.User.UserViewModel
import kotlinx.coroutines.launch

class GuestsActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var bookingViewModel: BookingViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guests)
    }

    fun summary_info(v:View){
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        context = this@GuestsActivity
        val spin1 = findViewById<View>(R.id.adults_spinner) as Spinner
        val spin2 = findViewById<View>(R.id.children_spinner) as Spinner
        val numberOfAdults = spin1.selectedView as TextView
        val numberOfChildren = spin2.selectedView as TextView
        val seniorGuestIf:String = findViewById<RadioButton>(findViewById<RadioGroup>(R.id.rd_group).checkedRadioButtonId).text.toString()

        val userId = sharedPreferences.getInt("user_id", 0)
        val cruiseId = sharedPreferences.getInt("cruise_id", 0)

        lifecycleScope.launch{
            var booking_id = bookingViewModel.insertBooking(
                context = context,
                customerId = userId,
                cruiseCode = cruiseId.toString(),
                numberOfAdults = 2,
                numberOfKids = 3,
                numberOfSeniors = 1,
                amoutPaid = 66.6,
                startDate = "11/20/2021",
                )

            sharedPreferences.edit().putLong(
                "booking_id", booking_id
            ).apply()
            Toast.makeText( context,"bookingId:${booking_id}", Toast.LENGTH_SHORT).show()
        }


//        val i = Intent(this@GuestsActivity, CheckoutActivity::class.java)
//        startActivity(i)
    }
}