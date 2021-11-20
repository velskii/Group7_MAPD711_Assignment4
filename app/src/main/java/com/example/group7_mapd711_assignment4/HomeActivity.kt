package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.User.UserViewModel

class HomeActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userViewModel: UserViewModel
    lateinit var context: Context

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

        sharedPreferences = this.getSharedPreferences("com.example.feiliangzhou_mapd711_assignment4", Context.MODE_PRIVATE)
        context = this@HomeActivity
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userId = sharedPreferences.getInt("user_id", 0)
        val username = sharedPreferences.getString("username", "no name")
        val password = sharedPreferences.getString("password", "no psw")

        findViewById<TextView>(R.id.tvOutputName).text = username
        findViewById<TextView>(R.id.tvOutputCourse).text = password
        findViewById<TextView>(R.id.userId).text = userId.toString()

    }

}