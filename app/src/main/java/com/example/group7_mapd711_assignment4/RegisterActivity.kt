package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userViewModel: UserViewModel
    lateinit var context: Context
    lateinit var username: String
    lateinit var password: String
    lateinit var passwordRepeat: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences = this.getSharedPreferences("com.example.feiliangzhou_mapd711_assignment4", Context.MODE_PRIVATE)
        context = this@RegisterActivity
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        var btnRegister: Button =findViewById(R.id.btnRegister)
        var editUsername: EditText =findViewById(R.id.username_register)
        var editPassword: EditText =findViewById(R.id.password_register)
        var editRepeatPassword: EditText =findViewById(R.id.repeat_password_register)

        btnRegister.setOnClickListener {
            username = editUsername.text.toString().trim()
            password = editPassword.text.toString().trim()
            passwordRepeat = editRepeatPassword.text.toString().trim()

            if (username.isEmpty()) {
                editUsername.error = "Enter Username"
            }
            else if (password.isEmpty()) {
                editPassword.error = "Please Enter password"
            }
            else if (passwordRepeat.isEmpty()) {
                editRepeatPassword.error = "Please Enter repeat password"
            }
            else if (passwordRepeat != password) {
                editRepeatPassword.error = "Not same with Password"
            }
            else {
                lifecycleScope.launch{
                    var user_id = userViewModel.insertUser(context, username, password)
//                    sharedPreferences.edit().putLong(
//                        "user_id", user_id
//                    ).apply()
//                  Toast.makeText( context,"Login successfully${user_id}", Toast.LENGTH_LONG).show()

                    val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(i)
                }


            }
        }

    }
}