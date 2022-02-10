package tdao.example.android_severalactivities_storeddata

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {

    /**No1-0: Get values from radio buttons*/
    // good to initialize variables here when used in several functions
    lateinit var radioGroup: RadioGroup
    lateinit var et: EditText

    /**No2-1: where we create a companion object*/
    // companion object - A singleton that acts like a static variable
    // so it can be accessed directly via the name of the containing class
    companion object {
        const val PROG_NAME = "SEVERALACTIVITIES"
        var name:String = ""
        var spinValue:Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize the lateinit variables
        radioGroup=findViewById(R.id.radioGroup)
        et = findViewById(R.id.editTextTextPersonName)
    }

    fun onClick(view: View) {
        /**No1-0: Get values from radio buttons*/
        var radValue:Int =0
        // get the value of the checked radio button
        var checkedRad:Int = radioGroup.checkedRadioButtonId
        when(checkedRad) {
            R.id.radioButton1-> radValue = 1
            R.id.radioButton2-> radValue = 2
            R.id.radioButton3-> radValue = 3
            R.id.radioButton4-> radValue = 4
        }

        when(view.id) {
            /**No1-1: where we create intent passed to other activities*/
            /**Bundles - Sharing data between activities*/
            R.id.buttonNewActivity -> {

                /**First approach using bundle passing to intent*/
//                // creating the instance of the bundle
//                val bundle = Bundle()
//
//                // storing the string value in the bundle
//                // which is mapped to key
//                bundle.putString("key1", et.getText().toString())
//                bundle.putInt("key2", radValue)
//
//                // creating an intent
//                intent = Intent(this@MainActivity, SecondActivity::class.java)
//
//                // passing a bundle to the intent
//                intent.putExtras(bundle)
//
//                // starting the activity by passing the intent to it.
//                startActivity(intent)

                // Bundling:  Concept to send information to another activity
                // putExtra adds key value pairs to the bundle which passes them to the new activity created
                // use this method to send data to new activities launched with startActivity
                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("key1", et.getText().toString())
                    putExtra("key2", radValue)
                }
                //starting the activity by passing the intent to it.
                startActivity(intent)
            }
        }
    }

    /**No2-3: Where we get data back from 2nd activity and others*/
    override fun onResume() {
        super.onResume()

        Log.d(PROG_NAME, spinValue.toString())
        Log.d(PROG_NAME, "onResume")
        // set the radiobutton checked to correct number from secondactivity
        when (spinValue) {
            1 -> radioGroup.check(R.id.radioButton1)
            2 -> radioGroup.check(R.id.radioButton2)
            3 -> radioGroup.check(R.id.radioButton3)
            4 -> radioGroup.check(R.id.radioButton4)
        }
        /**No3-2: open SharedPreferences get data after app resume*/
        /**Check data in Device File Explorer folder data -> data-> program name -> shared_prefs -> .xml file*/
        // reading the shared prefences to restore the edit text field with the last name in it before
        // leaving this acitivity
        val prefsEditor = getSharedPreferences(PROG_NAME, Context.MODE_PRIVATE)
        // second parameter "" what to set the editText if no shared pref file exists
        et.setText(prefsEditor.getString("name", ""))
    }

    override fun onPause() {
        super.onPause()

        /**No3-1: SharedPreferences when the application is closed*/
        // save the name in the EditText to the shared preference
        val prefsEditor = getSharedPreferences(PROG_NAME, Context.MODE_PRIVATE).edit()
        prefsEditor.putString("name", et.text.toString())
        prefsEditor.apply()
    }



}