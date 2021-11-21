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
import androidx.lifecycle.lifecycleScope
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.User.UserViewModel
import kotlinx.coroutines.launch
import java.util.*

class GuestsActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var bookingViewModel: BookingViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guests)


        findViewById<DatePicker>(R.id.datePicker).setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            findViewById<TextView>(R.id.dateDisplay).setText(" "+ dayOfMonth + "/" + monthOfYear + "/" + year )
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

    fun summary_info(v:View){
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        context = this@GuestsActivity

        val numberOfAdults = findViewById<Spinner>(R.id.adults_spinner).selectedView as TextView
        val numberOfChildren = findViewById<Spinner>(R.id.children_spinner).selectedView as TextView
        val seniorGuest = findViewById<RadioButton>(findViewById<RadioGroup>(R.id.rd_group).checkedRadioButtonId).text.toString()
        val startDate = findViewById<TextView>(R.id.dateDisplay).text.toString()


        val userId = sharedPreferences.getInt("user_id", 0)
        val cruiseId = sharedPreferences.getInt("cruise_id", 0)
        var seniorGuestIf: Int = 0
        if (seniorGuest == "YES") {
            seniorGuestIf = 1
        } else {
            seniorGuestIf = 0
        }
        Toast.makeText( context,"uid:${userId},cid:${cruiseId}", Toast.LENGTH_SHORT).show()



        lifecycleScope.launch{
            var booking_id = bookingViewModel.insertBooking(
                context = context,
                customerId = userId,
                cruiseCode = cruiseId.toString(),
                numberOfAdults = numberOfAdults.text.toString().toInt(),
                numberOfKids = numberOfChildren.text.toString().toInt(),
                numberOfSeniors = seniorGuestIf,
                amoutPaid = 66.6,
                startDate = startDate,
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