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
import android.widget.TextView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.util.Log
import com.example.group7_mapd711_assignment4.firebase.User

class UserInformationActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        context = this@UserInformationActivity

        val editUsername = findViewById<TextView>(R.id.username_show)
        val editPassword = findViewById<TextView>(R.id.password_show)
        val editFirstname = findViewById<TextView>(R.id.firstname_show)
        val editLastname = findViewById<TextView>(R.id.lastname_show)
        val editAddress = findViewById<TextView>(R.id.address_show)
        val editCity = findViewById<TextView>(R.id.city_show)
        val editPostalcode = findViewById<TextView>(R.id.postal_code_show)
        val editTelephone = findViewById<TextView>(R.id.telephone_show)
        val editEmail = findViewById<TextView>(R.id.email_show)

        val uid = sharedPreferences.getString("user_id_firebase", "")
        editUsername.setText(sharedPreferences.getString("username", ""))
        editPassword.setText(sharedPreferences.getString("password", ""))
        editEmail.setText(sharedPreferences.getString("email", ""))

        if (uid != null) {
            User(uid).getUserByUid(uid).addOnSuccessListener {
                if (it.child("username").value != null) {
                    editUsername.setText(it.child("username").value.toString())
                }
                if (it.child("password").value != null) {
                    editPassword.setText(it.child("password").value.toString())
                }
                if (it.child("firstname").value != null) {
                    editFirstname.setText(it.child("firstname").value.toString())
                }
                if (it.child("lastname").value != null) {
                    editLastname.setText(it.child("lastname").value.toString())
                }
                if (it.child("address").value != null) {
                    editAddress.setText(it.child("address").value.toString())
                }
                if (it.child("city").value != null) {
                    editCity.setText(it.child("city").value.toString())
                }
                if (it.child("postalcode").value != null) {
                    editPostalcode.setText(it.child("postalcode").value.toString())
                }
                if (it.child("telephone").value != null) {
                    editTelephone.setText(it.child("telephone").value.toString())
                }
                if (it.child("email").value != null) {
                    editEmail.setText(it.child("email").value.toString())
                }
            }.addOnFailureListener{
                Log.e(TAG, "Error getting data", it)
            }
        }

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
    companion object {
        const val TAG = "UserInformationActivity"
    }
}