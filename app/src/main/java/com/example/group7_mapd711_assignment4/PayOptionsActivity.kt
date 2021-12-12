/*
  MAPD 711 - Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
package com.example.group7_mapd711_assignment4

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.*

class PayOptionsActivity : AppCompatActivity() {

    var buttonChangeDate: Button? = null
    var textviewExpiryDate: TextView? = null
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_options)

        // get the references from layout file
        textviewExpiryDate = findViewById(R.id.tvExpiryDate)
        buttonChangeDate = findViewById(R.id.btn_change_date)

        //textview_date!!.text = "--/--/----"

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        buttonChangeDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@PayOptionsActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = java.text.SimpleDateFormat(myFormat, Locale.US)
        val currentDate = Date()
        if(cal.getTime().before(currentDate))
        {
            Toast.makeText(this@PayOptionsActivity, "Expiry date must be greater than today.", Toast.LENGTH_LONG).show()
        } else {
            textviewExpiryDate!!.text = sdf.format(cal.getTime())
        }
    }

    fun rdbCreditCard_Click(v:View)
    {
        if(v.id == R.id.rdb_credit_card)
        {
//            val layoutPickExpiryDate = findViewById<LinearLayout>(R.id.layout_pick_expiry_date)
//            val layoutExpiryDate = findViewById<LinearLayout>(R.id.layout_expiry_date)
//            val editTextCreditCard = findViewById<EditText>(R.id.edit_text_credit_card)
            val rdbPaymentOptions = findViewById<View>(R.id.rdb_payment_options) as RadioGroup
            val rdbPaymentOptionsId: Int = rdbPaymentOptions.checkedRadioButtonId

            if(rdbPaymentOptionsId != -1) {
                val selectedRadioButton: RadioButton = findViewById(rdbPaymentOptionsId)
                val rdbCreditCardText = selectedRadioButton.text.toString()
                if (rdbCreditCardText == "Credit Card") {
//                    layoutPickExpiryDate.visibility = View.VISIBLE
//                    layoutExpiryDate.visibility = View.VISIBLE
//                    editTextCreditCard.visibility = View.VISIBLE
                }
            }
        }
    }

    fun rdbCash_Click(v:View)
    {
        if(v.id == R.id.rdb_cash)
        {
            val rdbPaymentOptions = findViewById<View>(R.id.rdb_payment_options) as RadioGroup
            val rdbPaymentOptionsId: Int = rdbPaymentOptions.checkedRadioButtonId

            if(rdbPaymentOptionsId != -1) {
                val selectedRadioButton: RadioButton = findViewById(rdbPaymentOptionsId)
                val rdbCreditCardText = selectedRadioButton.text.toString()
                if (rdbCreditCardText == "Cash") {
//                    layoutPickExpiryDate.visibility = View.INVISIBLE
//                    layoutExpiryDate.visibility = View.INVISIBLE
//                    editTextCreditCard.visibility = View.INVISIBLE
                }
            }
        }
    }

    fun btnNext_Click(v:View)
    {
        val textExpiryDte = findViewById<TextView>(R.id.tvExpiryDate)
        val rdbPaymentOptions = findViewById<View>(R.id.rdb_payment_options) as RadioGroup
        val rdbPaymentOptionsId: Int = rdbPaymentOptions.checkedRadioButtonId
        val intent = Intent(this@PayOptionsActivity, HomeActivity::class.java)
        if(rdbPaymentOptionsId != -1) {
            val selectedRadioButton: RadioButton = findViewById(rdbPaymentOptionsId)
            val rdbCreditCardText = selectedRadioButton.text.toString()
            if (rdbCreditCardText == "Credit Card") {
                val cardNumber = findViewById<EditText>(R.id.edit_text_credit_card)
                if (cardNumber.text.length != 8) {
                    Toast.makeText(
                        this@PayOptionsActivity,
                        "Card Number must be 8 digits",
                        Toast.LENGTH_LONG
                    ).show()
                } else if(textExpiryDte.text.toString().equals("--/--/----")) {
                    Toast.makeText(
                        this@PayOptionsActivity,
                        "Expiry Date must not be empty",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    this.showDialog()
                }
            } else {
                val cashNumber = findViewById<EditText>(R.id.edit_text_cash_card)
                if (cashNumber.text.toString().toInt() < 100) {
                    Toast.makeText(
                        this@PayOptionsActivity,
                        "Cash must be more than 100",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
                this.showDialog()
            }
        }
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Booking successfully!")
        builder.setMessage("Go back home activity?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            val intent = Intent(this@PayOptionsActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            val intent = Intent(this@PayOptionsActivity, BookingListActivity::class.java)
            startActivity(intent)
        }
        builder.show()
    }

}