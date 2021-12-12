/*
  MAPD 711 - Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
package com.example.group7_mapd711_assignment4

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.Cruise.CruiseViewModel
import com.example.group7_mapd711_assignment4.firebase.Booking
import com.example.group7_mapd711_assignment4.firebase.Cruise
import java.util.*

class GuestsActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var bookingViewModel: BookingViewModel
    lateinit var cruiseViewModel: CruiseViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guests)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        cruiseViewModel = ViewModelProvider(this).get(CruiseViewModel::class.java)
        context = this@GuestsActivity

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)+1
        val day = c.get(Calendar.DAY_OF_MONTH)
        findViewById<TextView>(R.id.dateDisplay).setText(" "+ day + "/" + month + "/" + year)

        findViewById<DatePicker>(R.id.datePicker).setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val displayMonth: Int = monthOfYear + 1
            findViewById<TextView>(R.id.dateDisplay).setText(" "+ dayOfMonth + "/" + displayMonth + "/" + year )
        }

        val userId = sharedPreferences.getString("user_id_firebase", "0")
        var cruiseCode: String = ""
        var cruiseName: String = ""
        var cruiseDuration: String = ""
        var price: String = ""

        if (userId != null) {
            Cruise(userId).getCruiseById(userId).addOnSuccessListener {
//                Log.e("zz", it.child("cruiseName").value.toString())
                cruiseCode = it.child("cruiseCode").value.toString()
                cruiseName = it.child("cruiseName").value.toString()
                cruiseDuration = it.child("duration").value.toString()
                price = it.child("price").value.toString()
                findViewById<TextView>(R.id.amountPay).setText(price)
            }
        }

        val btnSub = findViewById<Button>(R.id.guestsSubmit)
        btnSub.setOnClickListener{
            if (userId != null) {
                val bookingId = Booking(userId).createBooking(
//                    cruiseCode = cruiseCode,
                    cruiseCode = cruiseName + " / " + cruiseDuration + " days",
                    numberOfAdults = (findViewById<Spinner>(R.id.adults_spinner).selectedView as TextView).text.toString().toInt(),
                    numberOfKids = (findViewById<Spinner>(R.id.children_spinner).selectedView as TextView).text.toString().toInt(),
                    numberOfSeniors = (findViewById<Spinner>(R.id.senior_spinner).selectedView as TextView).text.toString().toInt(),
                    amountPaid = price,
                    startDate = findViewById<TextView>(R.id.dateDisplay).text.toString()
                )
                sharedPreferences.edit().putString("booking_id", bookingId).apply()
            }
            val i = Intent(this@GuestsActivity, CheckoutActivity::class.java)
            startActivity(i)
        }
    }

    fun datePicking(v:View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this@GuestsActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            findViewById<TextView>(R.id.dateDisplay).setText(" "+ dayOfMonth + "/" + monthOfYear + "/" + year )
        }, year, month, day)
        dpd.show()
    }

}