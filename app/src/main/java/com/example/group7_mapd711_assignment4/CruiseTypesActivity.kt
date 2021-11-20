package com.example.group7_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.group7_mapd711_assignment4.Fragments.*

class CruiseTypesActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var cruiseTypeChecked :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cruise_types)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun chooseMembers(v:View){
        val cruiseTypeCheckBox = findViewById<CheckBox>(R.id.cruise_type)
        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment2", Context.MODE_PRIVATE)

        if(cruiseTypeCheckBox.isChecked)
        {
            cruiseTypeChecked = cruiseTypeCheckBox.text.toString()
            var price : String = findViewById<TextView>(R.id.price).text.toString()
            var visiting_places : String = findViewById<TextView>(R.id.visiting_places).text.toString()
            var duration : String = findViewById<TextView>(R.id.duration).text.toString()
            sharedPreferences.edit().putString(
                "price", price
            ).apply()
            sharedPreferences.edit().putString(
                "visiting_places", visiting_places
            ).apply()
            sharedPreferences.edit().putString(
                "duration", duration
            ).apply()
            sharedPreferences.edit().putString(
                "cruise_type_checked", cruiseTypeChecked
            ).apply()
            val i = Intent(this@CruiseTypesActivity, MembersActivity::class.java)
            startActivity(i)
        } else {
            Toast.makeText(this,"Please select one cruise type",Toast.LENGTH_LONG).show()
        }
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