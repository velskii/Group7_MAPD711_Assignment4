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
import android.util.Log
import android.view.View
import android.widget.*
import com.example.group7_mapd711_assignment4.firebase.Booking

class BookingListActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var context: Context
    var list = emptyArray<String>()
    var idList = emptyArray<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_list)

        context = this@BookingListActivity
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id_firebase", "0")

        if (userId != null) {
            Booking(userId).getBookingByUid(userId).addOnSuccessListener{
                if (it.children.count() <= 0) {
                    val tvBookingMsg = findViewById<TextView>(R.id.tvBookingMsg)
                    tvBookingMsg.setText("There is no booking found.")
                } else {
                    for (b in it.children) {
                        list += ("BookingId: " + b.child("id").value.toString() + "\nCruise: " + b.child("cruiseCode").value.toString())
                        idList += b.child("id").value.toString()
                    }
                }
                showList(list)
            }
        }




        val btnBackHome: Button = findViewById<View>(R.id.btnBackHome) as Button
        btnBackHome.setOnClickListener{
            val i = Intent(this@BookingListActivity, HomeActivity::class.java)
            startActivity(i);
        }

    }

    fun showList(list: Array<String>){
        val listView = findViewById<ListView>(R.id.bookings_list_view)

        listView.adapter = ArrayAdapter(this,
            R.layout.list_view_item, list)

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {
//                val itemValue = listView.getItemAtPosition(position) as String

                sharedPreferences.edit().putString(
//                    "booking_id_selected", itemValue.substringAfter("BookingId: ").substringBefore("Cruise:").trim()
                    "booking_id_selected", idList[position]
                ).apply()

                val i = Intent(this@BookingListActivity, BookingInformationActivity::class.java)
                startActivity(i);
            }
        }
    }
}