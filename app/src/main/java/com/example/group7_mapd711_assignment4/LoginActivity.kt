package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userViewModel: UserViewModel
    lateinit var context: Context
    lateinit var username: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = this.getSharedPreferences("com.example.feiliangzhou_mapd711_assignment4", Context.MODE_PRIVATE)

        context = this@LoginActivity

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        var btnLogin: Button =findViewById(R.id.btnLogin)
        var editUsername: EditText =findViewById(R.id.username_login)
        var editPassword: EditText =findViewById(R.id.password_login)

        btnLogin.setOnClickListener {
            username = editUsername.text.toString().trim()
            password = editPassword.text.toString().trim()

            if (username.isEmpty()) {
                editUsername.error = "Enter Username"
            }
            else if (password.isEmpty()) {
                editPassword.error = "Please Enter password"
            }
            else {
                userViewModel.getUsersByUsernameAndPassword(context, username, password)!!.observe(this, Observer {
                    if (it == null) {
                        Toast.makeText( context,"User doesn't exist, Please register first.", Toast.LENGTH_LONG).show()
                    }
                    else {
                        it.id?.let { it1 ->
                            sharedPreferences.edit().putInt(
                                "user_id", it1
                            ).apply()
                        }
                        sharedPreferences.edit().putString(
                            "username", it.username
                        ).apply()
                        sharedPreferences.edit().putString(
                            "password", it.password
                        ).apply()

//                        Toast.makeText( context,"Login successfully.", Toast.LENGTH_SHORT).show()
                        val i = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                })

            }
        }

    }
}