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
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.firebase.Booking

class BookingInformationActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var bookingViewModel: BookingViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_information)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        context = this@BookingInformationActivity
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)

        val booking_id_selected = sharedPreferences.getString("booking_id_selected", "0")
        val userId = sharedPreferences.getString("user_id_firebase", "0")

        val tvCruiseCode = findViewById<TextView>(R.id.tvCruiseCode)
        val tvNoOfAdults = findViewById<TextView>(R.id.tvNoOfAdults)
        val tvNoOfKids = findViewById<TextView>(R.id.tvNoOfKids)
        val tvNoOfSeniors = findViewById<TextView>(R.id.tvNoOfSeniors)
        val tvAmountPaid = findViewById<TextView>(R.id.tvAmountPaid)
        val tvStartDate = findViewById<TextView>(R.id.tvStartDate)

        if (userId != null && booking_id_selected != null) {
            Booking(userId).getBookingByUidAndBid(userId, booking_id_selected).addOnSuccessListener{
                tvCruiseCode.setText(it.child("cruiseCode").value.toString())
                tvNoOfAdults.setText(it.child("numberOfAdults").value.toString())
                tvNoOfKids.setText(it.child("numberOfKids").value.toString())
                tvNoOfSeniors.setText(it.child("numberOfSeniors").value.toString())
                tvAmountPaid.setText(it.child("amountPaid").value.toString())
                tvStartDate.setText(it.child("startDate").value.toString())
            }
        }

        // Update booking information
        val btnUpdate: Button = findViewById<View>(R.id.button_update) as Button
        btnUpdate.setOnClickListener{
            val i = Intent(this@BookingInformationActivity, UpdateBookingActivity::class.java)
            startActivity(i);
        }
        // Cancel booking
        val btnCancel: Button = findViewById<View>(R.id.button_cancel_booking) as Button
        btnCancel.setOnClickListener{
            val builder = AlertDialog.Builder(this@BookingInformationActivity)
            builder.setMessage("Are you sure you want to cancel this booking?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    //if(bookingId != 0) {
                    if (userId != null && booking_id_selected != null) {
                        Booking(userId).deleteByUidAndBid(userId, booking_id_selected)
                //                    bookingViewModel.deleteBookingById(context, booking_id_selected)
                        Toast.makeText( context,"Cancel booking successfully!!!", Toast.LENGTH_LONG).show()
                    }
                    val i = Intent(this@BookingInformationActivity, BookingListActivity::class.java)
                    startActivity(i);
                    //}
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
        // Back to Booking List screen
        val btnHome: Button = findViewById<View>(R.id.button_back_home) as Button
        btnHome.setOnClickListener{
            val i = Intent(this@BookingInformationActivity, BookingListActivity::class.java)
            startActivity(i);
        }
    }
}