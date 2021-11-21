package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.User.UserViewModel

class UpdateBookingActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var bookingViewModel: BookingViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_booking)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        context = this@UpdateBookingActivity
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)

        val userId = sharedPreferences.getInt("user_id", 0)
        val bookingIdSelected = sharedPreferences.getInt("booking_id_selected", 0)

        val tvCruiseCode = findViewById<TextView>(R.id.tvCruiseCode)
        val editNoOfAdults = findViewById<EditText>(R.id.tvNoOfAdults)
        val editNoOfKids = findViewById<EditText>(R.id.tvNoOfKids)
        val editNoOfSeniors = findViewById<EditText>(R.id.tvNoOfSeniors)
        val tvAmountPaid = findViewById<TextView>(R.id.tvAmountPaid)
        val tvStartDate = findViewById<TextView>(R.id.tvStartDate)

//        bookingViewModel.getBooking(context, userId)!!.observe(this, Observer {
        bookingViewModel.getBookingById(context, bookingIdSelected)!!.observe(this, Observer {
            if (it != null) {
                tvCruiseCode.setText(it.CruiseCode)
                editNoOfAdults.setText(it.NumberOfAdults.toString())
                editNoOfKids.setText(it.NumberOfKids.toString())
                editNoOfSeniors.setText(it.NumberOfSeniors.toString())
                tvAmountPaid.setText(it.AmountPaid.toString())
                tvStartDate.setText(it.StartDate)
            }
        })

        val btnUpdate: Button = findViewById<View>(R.id.button_update) as Button
        btnUpdate.setOnClickListener{
            val noOfAdults = editNoOfAdults.text.toString()
            val noOfKids = editNoOfKids.text.toString()
            val noOfSeniors = editNoOfSeniors.text.toString()

            if (noOfAdults.isDigitsOnly() && noOfKids.isDigitsOnly() && noOfSeniors.isDigitsOnly()) {
                bookingViewModel.updateBooking(context = context, id = userId, numberOfAdults = noOfAdults.toInt(),
                    numberOfKids = noOfKids.toInt(), numberOfSeniors = noOfSeniors.toInt())

                val i = Intent(this@UpdateBookingActivity, BookingInformationActivity::class.java)
                startActivity(i);
//                userViewModel.getUsersById(context, userId)!!.observe(this, Observer {
//
//                    if (it != null) {
//                        Toast.makeText(context, "${it.firstname}", Toast.LENGTH_LONG).show()
//                        val i = Intent(this@UpdateUserActivity, UserInformationActivity::class.java)
//                        startActivity(i);
//                    }
//                })

            } else {
                Toast.makeText( context,"Please enter digit only for number of adults, kids, seniors fields.", Toast.LENGTH_LONG).show()
            }
        }

        val btnBack: Button = findViewById<View>(R.id.button_cancel) as Button
        btnBack.setOnClickListener{
            val i = Intent(this@UpdateBookingActivity, BookingInformationActivity::class.java)
            startActivity(i);
        }
    }
}