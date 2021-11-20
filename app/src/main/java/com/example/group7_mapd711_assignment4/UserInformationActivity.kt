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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.User.UserViewModel

class UserInformationActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userViewModel: UserViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        sharedPreferences = this.getSharedPreferences("com.example.feiliangzhou_mapd711_assignment4", Context.MODE_PRIVATE)
        context = this@UserInformationActivity
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userId = sharedPreferences.getInt("user_id", 0)

        val editUsername = findViewById<TextView>(R.id.username_show)
        val editPassword = findViewById<TextView>(R.id.password_show)
        val editFirstname = findViewById<TextView>(R.id.firstname_show)
        val editLastname = findViewById<TextView>(R.id.lastname_show)
        val editAddress = findViewById<TextView>(R.id.address_show)
        val editCity = findViewById<TextView>(R.id.city_show)
        val editPostalcode = findViewById<TextView>(R.id.postal_code_show)
        val editTelephone = findViewById<TextView>(R.id.telephone_show)
        val editEmail = findViewById<TextView>(R.id.email_show)
        userViewModel.getUsersById(context, userId)!!.observe(this, Observer {
            if (it != null) {
                editUsername.setText(it.username)
                editPassword.setText(it.password)
                editFirstname.setText(it.firstname)
                editLastname.setText(it.lastname)
                editAddress.setText(it.address)
                editCity.setText(it.city)
                editPostalcode.setText(it.postalcode)
                editTelephone.setText(it.telephone)
                editEmail.setText(it.email)
            }
        })

        val btnChange: Button = findViewById<View>(R.id.btnChange) as Button
        btnChange.setOnClickListener{
            val i = Intent(this@UserInformationActivity, UpdateUserActivity::class.java)
            startActivity(i);
        }
        val btnBackHome: Button = findViewById<View>(R.id.btnBackHome) as Button
        btnBackHome.setOnClickListener{
            val i = Intent(this@UserInformationActivity, HomeActivity::class.java)
            startActivity(i);
        }
    }
}