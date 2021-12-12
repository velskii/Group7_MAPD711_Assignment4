/*
  MAPD 711 - Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.firebase.Booking

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

        val bookingIdSelected = sharedPreferences.getString("booking_id_selected", "0")
        val userId = sharedPreferences.getString("user_id_firebase", "0")

        val tvCruiseCode = findViewById<TextView>(R.id.tvCruiseCode)
        val editNoOfAdults = findViewById<Spinner>(R.id.no_of_adults)
        val editNoOfKids = findViewById<Spinner>(R.id.no_of_kids)
        val editNoOfSeniors = findViewById<Spinner>(R.id.no_of_seniors)
        val tvAmountPaid = findViewById<TextView>(R.id.tvAmountPaid)
        val tvStartDate = findViewById<TextView>(R.id.tvStartDate)

        if (userId != null && bookingIdSelected != null) {
            Booking(userId).getBookingByUidAndBid(userId, bookingIdSelected).addOnSuccessListener{
                tvCruiseCode.setText(it.child("cruiseCode").value.toString())
                editNoOfAdults.setSelection(it.child("numberOfAdults").value.toString().toInt() -1)
                editNoOfKids.setSelection(it.child("numberOfKids").value.toString().toInt() -1)
                editNoOfSeniors.setSelection(it.child("numberOfSeniors").value.toString().toInt() -1)
                tvAmountPaid.setText(it.child("amountPaid").value.toString())
                tvStartDate.setText(it.child("startDate").value.toString())
            }
        }

        val btnUpdate: Button = findViewById<View>(R.id.button_update) as Button
        // Update action
        btnUpdate.setOnClickListener{
            val noOfAdults = editNoOfAdults.selectedItem.toString()
            val noOfKids = editNoOfKids.selectedItem.toString()
            val noOfSeniors = editNoOfSeniors.selectedItem.toString()
            val cruiseCode = tvCruiseCode.text.toString()
            val amountPaid = tvAmountPaid.text.toString()
            val startDate = tvStartDate.text.toString()

            if (noOfAdults.isDigitsOnly() && noOfKids.isDigitsOnly() && noOfSeniors.isDigitsOnly()) {
//                bookingViewModel.updateBooking(context = context, id = bookingId, numberOfAdults = noOfAdults.toInt(),
//                    numberOfKids = noOfKids.toInt(), numberOfSeniors = noOfSeniors.toInt())
                if (userId != null && bookingIdSelected != null) {
                    Booking(userId).updateBooking(
                        id = bookingIdSelected,
                        cruiseCode = cruiseCode,
                        numberOfAdults = noOfAdults.toInt(),
                        numberOfKids = noOfKids.toInt(),
                        numberOfSeniors = noOfSeniors.toInt(),
                        amountPaid = amountPaid,
                        startDate = startDate
                    )
                }
                val i = Intent(this@UpdateBookingActivity, BookingInformationActivity::class.java)
                startActivity(i);
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