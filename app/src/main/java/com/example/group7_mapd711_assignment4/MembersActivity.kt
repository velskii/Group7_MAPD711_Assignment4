package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MembersActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
    }

    fun summary_info(v:View){
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment2", Context.MODE_PRIVATE)

        val spin1 = findViewById<View>(R.id.adults_spinner) as Spinner
        val spin2 = findViewById<View>(R.id.children_spinner) as Spinner
        val numberOfAdults = spin1.selectedView as TextView
        val numberOfChildren = spin2.selectedView as TextView
        val radioGroup = findViewById<View>(R.id.rd_group) as RadioGroup
//        var senior_guest:Boolean = false
//        radioGroup.setOnCheckedChangeListener { _, _ ->
//            val rb1 = findViewById<View>(R.id.rdb1) as RadioButton
//            senior_guest = rb1.isChecked
//        }
        val buttonId: Int = radioGroup.checkedRadioButtonId

        val selectedRadioButton: RadioButton = findViewById(buttonId)
        val senior_guest:String = selectedRadioButton.text.toString()

        sharedPreferences.edit().putString(
            "numberOfAdults", numberOfAdults.text.toString()
        ).apply()
        sharedPreferences.edit().putString(
            "numberOfChildren", numberOfChildren.text.toString()
        ).apply()
        sharedPreferences.edit().putString(
            "senior_guest", senior_guest
        ).apply()

        val i = Intent(this@MembersActivity, CheckoutActivity::class.java)
        startActivity(i)
    }
}