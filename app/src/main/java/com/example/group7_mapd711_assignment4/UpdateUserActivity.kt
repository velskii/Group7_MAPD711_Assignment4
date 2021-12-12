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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.User.UserViewModel
import com.google.firebase.ktx.Firebase
import android.util.Log
import com.example.group7_mapd711_assignment4.firebase.User
import com.google.firebase.auth.ktx.auth

class UpdateUserActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userViewModel: UserViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        context = this@UpdateUserActivity
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val username = sharedPreferences.getString("username", "no name")
        val password = sharedPreferences.getString("password", "no psw")

        findViewById<EditText>(R.id.username_update).setText(username)
        findViewById<EditText>(R.id.password_update).setText(password)

        val editFirstname = findViewById<EditText>(R.id.firstname_update)
        val editLastname = findViewById<EditText>(R.id.lastname_update)
        val editAddress = findViewById<EditText>(R.id.address_update)
        val editCity = findViewById<EditText>(R.id.city_update)
        val editPostalcode = findViewById<EditText>(R.id.postal_code_update)
        val editTelephone = findViewById<EditText>(R.id.telephone_update)
        val editEmail = findViewById<EditText>(R.id.email_update)

        val userData = Firebase.auth.currentUser
        userData?.let {
            // Name, email address, and profile photo Url
            val name = userData.displayName
            val email = userData.email
            val uid = userData.uid

            User(uid).getUserByUid(uid).addOnSuccessListener {
//            userTable.child(uid).get().addOnSuccessListener {
                editFirstname.setText(it.child("firstname").value.toString())
                editLastname.setText(it.child("lastname").value.toString())
                editAddress.setText(it.child("address").value.toString())
                editCity.setText(it.child("city").value.toString())
                editPostalcode.setText(it.child("postalcode").value.toString())
                editTelephone.setText(it.child("telephone").value.toString())
                editEmail.setText(it.child("email").value.toString())
            }
        }

        val btnUpdate: Button = findViewById<View>(R.id.btnUpdate) as Button
        btnUpdate.setOnClickListener{
            val firstname = editFirstname.text.toString()
            val lastname = editLastname.text.toString()
            val address = editAddress.text.toString()
            val city = editCity.text.toString()
            val postalcode = editPostalcode.text.toString()
            val telephone = editTelephone.text.toString()
            val email = editEmail.text.toString()

            if ((username != null) && (password != null)) {
                userData?.let {
//                    val name = userData.displayName
//                    val email = userData.email
                    val uid = userData.uid
                    if (email != null) {
                        User(uid).writeNewUser(username, password, firstname, lastname, address, city, postalcode, telephone, email)
                    }
                }
                val i = Intent(this@UpdateUserActivity, UserInformationActivity::class.java)
                startActivity(i);
            } else {
                Toast.makeText( context,"Please fill in the empty fields.", Toast.LENGTH_LONG).show()
            }
        }

        val btnBackHome: Button = findViewById<View>(R.id.btnBackHome) as Button
        btnBackHome.setOnClickListener{
            val i = Intent(this@UpdateUserActivity, HomeActivity::class.java)
            startActivity(i);
        }

    }
}