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
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.group7_mapd711_assignment4.User.UserViewModel
import kotlinx.coroutines.launch
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userViewModel: UserViewModel
    lateinit var context: Context
    lateinit var email: String
    lateinit var password: String
    lateinit var passwordRepeat: String

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        context = this@RegisterActivity
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        var btnRegister: Button =findViewById(R.id.btnRegister)
        var editEmail: EditText =findViewById(R.id.email_register)
        var editPassword: EditText =findViewById(R.id.password_register)
        var editRepeatPassword: EditText =findViewById(R.id.repeat_password_register)

        btnRegister.setOnClickListener {
            email = editEmail.text.toString().trim()
            password = editPassword.text.toString().trim()
            passwordRepeat = editRepeatPassword.text.toString().trim()

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editEmail.error = "Enter the Email with right format"
            } else if (password.isEmpty() || (password.length < 6)) {
                editPassword.error = "Please Enter password more than 6 digits"
            }
            else if (passwordRepeat.isEmpty()) {
                editRepeatPassword.error = "Please Enter repeat password"
            }
            else if (passwordRepeat != password) {
                editRepeatPassword.error = "Not same with Password"
            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this)
                    { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                    }
            }
        }

        val linkLogin: Button = findViewById<View>(R.id.linkLogin) as Button
        linkLogin.setOnClickListener{
            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(i)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val i = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(i)
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}