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
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.User.UserViewModel

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

        val userId = sharedPreferences.getInt("user_id", 0)
        val username = sharedPreferences.getString("username", "no name")
        val password = sharedPreferences.getString("password", "no psw")

        findViewById<EditText>(R.id.username_update).setText(username)
        findViewById<EditText>(R.id.password_update).setText(password)
//        findViewById<EditText>(R.id.userId).text = userId.toString()

        val editFirstname = findViewById<EditText>(R.id.firstname_update)
        val editLastname = findViewById<EditText>(R.id.lastname_update)
        val editAddress = findViewById<EditText>(R.id.address_update)
        val editCity = findViewById<EditText>(R.id.city_update)
        val editPostalcode = findViewById<EditText>(R.id.postal_code_update)
        val editTelephone = findViewById<EditText>(R.id.telephone_update)
        val editEmail = findViewById<EditText>(R.id.email_update)
        userViewModel.getUsersById(context, userId)!!.observe(this, Observer {
            if (it != null) {
                editFirstname.setText(it.firstname)
                editLastname.setText(it.lastname)
                editAddress.setText(it.address)
                editCity.setText(it.city)
                editPostalcode.setText(it.postalcode)
                editTelephone.setText(it.telephone)
                editEmail.setText(it.email)
            }
        })

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
                userViewModel.updateUser(context = context, id = userId, username = username, password = password, firstname = firstname, lastname = lastname, address = address, city = city, postalcode = postalcode, telephone = telephone, email = email )

                val i = Intent(this@UpdateUserActivity, UserInformationActivity::class.java)
                startActivity(i);
//                userViewModel.getUsersById(context, userId)!!.observe(this, Observer {
//
//                    if (it != null) {
//                        Toast.makeText(context, "${it.firstname}", Toast.LENGTH_LONG).show()
//                        val i = Intent(this@UpdateUserActivity, UserInformationActivity::class.java)
//                        startActivity(i);
//                    }
//                })

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