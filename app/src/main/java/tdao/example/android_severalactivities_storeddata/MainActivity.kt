package tdao.example.android_severalactivities_storeddata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {

    /**No1-0: Get values from radio buttons*/
    // good to initialize variables here when used in several functions
    lateinit var radioGroup: RadioGroup
    lateinit var et: EditText

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
}