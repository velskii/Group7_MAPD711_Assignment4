package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

class PersonalInformationActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_information)
    }

    fun job_done(v:View){
        var errorInfo : String = ""
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)

        val full_name = findViewById<EditText>(R.id.full_name).text.toString()
        val address = findViewById<EditText>(R.id.address).text.toString()
        val city = findViewById<EditText>(R.id.city).text.toString()
        val country = findViewById<EditText>(R.id.country).text.toString()
        val postal_code = findViewById<EditText>(R.id.postal_code).text.toString()
        val telephone_number = findViewById<EditText>(R.id.telephone_number).text.toString()
        val email = findViewById<EditText>(R.id.email).text.toString()

        if (! Pattern.matches("\\w{3,20}", full_name)){
            errorInfo = "full name length 3-20(contains only words)"
        } else if(! Pattern.matches(".{3,20}", address)){
            errorInfo = "address length 3-20(contains numbers or words)"
        } else if(! Pattern.matches("\\w{3,20}", city)) {
            errorInfo = "city length 3-20(contains only words)"
        } else if(! Pattern.matches("\\w{3,20}", country)){
            errorInfo = "country length 3-20(contains only words)"
        } else if(! Pattern.matches(".{6}", postal_code)){
            errorInfo = "postal code length 6(contains numbers or words)"
        } else if(! Pattern.matches("\\d{11}", telephone_number)){
            errorInfo = "telephone length 11(contains only numbers)"
        } else if(! Pattern.matches(".+\\@.+\\..+", email)){
            errorInfo = "email(contains numbers or words)"
        } else {
            errorInfo = ""
        }

        sharedPreferences.edit().putString(
            "full_name", full_name
        ).apply()
        sharedPreferences.edit().putString(
            "address", address
        ).apply()
        sharedPreferences.edit().putString(
            "city", city
        ).apply()
        sharedPreferences.edit().putString(
            "country", country
        ).apply()


        if (errorInfo == ""){
            val i = Intent(this@PersonalInformationActivity, SummaryActivity::class.java)
            startActivity(i)
        } else {
            Toast.makeText(this, errorInfo, Toast.LENGTH_SHORT).show()
        }

    }
}