package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.group7_mapd711_assignment4.User.UserViewModel

class MainActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel
    lateinit var context: Context
    lateinit var username: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linkLogin: Button = findViewById<View>(R.id.linkLogin) as Button
        linkLogin.setOnClickListener{
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(i)
        }

        val linkRegister: Button = findViewById(R.id.linkRegister) as Button
        linkRegister.setOnClickListener{
            val i = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(i)
        }


    }


}