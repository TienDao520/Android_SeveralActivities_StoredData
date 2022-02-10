package tdao.example.android_severalactivities_storeddata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {

    /**No1-0: Get values from radio buttons*/
    // good to initialize variables here when used in several functions
    lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize the lateinit variables
        radioGroup=findViewById(R.id.radioGroup)
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

    }
}