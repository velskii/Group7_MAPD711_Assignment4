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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.group7_mapd711_assignment4.Cruise.CruiseViewModel
import com.example.group7_mapd711_assignment4.Fragments.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import com.example.group7_mapd711_assignment4.firebase.Cruise
import com.example.group7_mapd711_assignment4.firebase.User

class CruiseTypesActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var cruiseTypeChecked :String = ""
    lateinit var cruiseViewModel : CruiseViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cruise_types)

        val btnBackHome: Button = findViewById<View>(R.id.btnBackHome) as Button
        btnBackHome.setOnClickListener{
            val i = Intent(this@CruiseTypesActivity, HomeActivity::class.java)
            startActivity(i);
        }
    }

    fun cruiseTypeChecked(v:View) {
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        context = this@CruiseTypesActivity
        cruiseViewModel = ViewModelProvider(this).get(CruiseViewModel::class.java)

        val cruiseTypeCheckBox = findViewById<CheckBox>(R.id.cruise_type)

        if(cruiseTypeCheckBox.isChecked)
        {
           cruiseTypeChecked = cruiseTypeCheckBox.text.toString()
            var price : String = findViewById<TextView>(R.id.price).text.toString()
            var visiting_places : String = findViewById<TextView>(R.id.visiting_places).text.toString()
            var duration : String = findViewById<TextView>(R.id.duration).text.toString()

            val userData = Firebase.auth.currentUser
            userData?.let {
                val uid = userData.uid
                val cruiseId = Cruise(uid).updateCruise(
//                    cruiseCode = "CODE_${cruiseTypeChecked}",
                    cruiseCode = cruiseTypeChecked + " / " + duration + " days",
                    cruiseName = cruiseTypeChecked,
                    visitingPlaces = visiting_places,
                    price = price,
                    duration = duration,
                )
                sharedPreferences.edit().putString(
                    "cruise_id", cruiseId
                ).apply()
            }

            val i = Intent(this@CruiseTypesActivity, GuestsActivity::class.java)
            startActivity(i)
        } else {
            Toast.makeText(this,"Please select one cruise type",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId){
            R.id.bahamas -> {
                fragment = BahamasFragment()
            }
            R.id.caribbean -> {
                fragment = CaribbeanFragment()
            }
            R.id.cuba -> {
                fragment = CubaFragment()
            }
            R.id.sampler -> {
                fragment = SamplerFragment()
            }
            R.id.star -> {
                fragment = StarFragment()
            }
        }
        if (fragment != null) {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_area, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return super.onOptionsItemSelected(item)
    }
}