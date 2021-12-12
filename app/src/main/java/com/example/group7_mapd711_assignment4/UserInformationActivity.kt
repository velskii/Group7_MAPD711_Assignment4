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
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.User.UserViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.util.Log
import com.example.group7_mapd711_assignment4.firebase.User

class UserInformationActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userViewModel: UserViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        context = this@UserInformationActivity
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

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

        if (uid != null) {
            User(uid).getUserByUid(uid).addOnSuccessListener {
//            userTable.child(uid).get().addOnSuccessListener {
                var un = ""
                var pw = ""
                var fn = ""
                var ln = ""
                var ad = ""
                var ct = ""
                var pc = ""
                var tp = ""
                var em = ""
                if (it.child("username").value != null) {
                    un = it.child("username").value.toString()
                }
                if (it.child("password").value != null) {
                    pw = it.child("password").value.toString()
                }
                if (it.child("firstname").value != null) {
                    fn = it.child("firstname").value.toString()
                }
                if (it.child("lastname").value != null) {
                    ln = it.child("lastname").value.toString()
                }
                if (it.child("address").value != null) {
                    ad = it.child("address").value.toString()
                }
                if (it.child("city").value != null) {
                    ct = it.child("city").value.toString()
                }
                if (it.child("postalcode").value != null) {
                    pc = it.child("postalcode").value.toString()
                }
                if (it.child("telephone").value != null) {
                    tp = it.child("telephone").value.toString()
                }
                if (it.child("email").value != null) {
                    em = it.child("email").value.toString()
                }

                editUsername.setText(un)
                editPassword.setText(pw)
                editFirstname.setText(fn)
                editLastname.setText(ln)
                editAddress.setText(ad)
                editCity.setText(ct)
                editPostalcode.setText(pc)
                editTelephone.setText(tp)
                editEmail.setText(em)
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