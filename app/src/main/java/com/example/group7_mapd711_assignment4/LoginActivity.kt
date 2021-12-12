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
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.User.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userViewModel: UserViewModel
    lateinit var context: Context
    lateinit var email: String
    lateinit var password: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        context = this@LoginActivity
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        var btnLogin: Button =findViewById(R.id.btnLogin)
        var editEmail: EditText =findViewById(R.id.email_login)
        var editPassword: EditText =findViewById(R.id.password_login)

        btnLogin.setOnClickListener {
            email = editEmail.text.toString().trim()
            password = editPassword.text.toString().trim()

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editEmail.error = "Enter the Email with right format"
            } else if (password.isEmpty()|| (password.length < 6)) {
                editPassword.error = "Please Enter password more than 6 digits"
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this)
                    { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        val linkRegister: Button = findViewById(R.id.linkRegister) as Button
        linkRegister.setOnClickListener{
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            val emailVerified = user.isEmailVerified
            val uid = user.uid

            sharedPreferences.edit().putString(
                "user_id_firebase", uid
            ).apply()
            if (email != null) {
                sharedPreferences.edit().putString(
                    "username", email.substringBefore("@")
                ).apply()
                sharedPreferences.edit().putString(
                    "email", email
                ).apply()
            }
            sharedPreferences.edit().putString(
                "password", password
            ).apply()
        }

        val i = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(i)
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}