package tdao.example.android_severalactivities_storeddata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    lateinit var spinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // getting the bundle back from the android
        val bundle = intent.extras

        /**No1-2: where we get data from other activities*/
        // performing the safety null check
        var s:String? = "test"
        // getting the string back, notice the second parameter is the default
        s = bundle!!.getString("key1", "Default")
        // getting the int back, notice the second parameter is the default
        var i:Int = bundle.getInt("key2", 4)
        // set the textView to the name passed in through the bundle
        var tv: TextView = findViewById(R.id.textViewNameSecond)
        tv.setText(s)
        // set the spinner to the value of the radio button passed in through the bundle
        spinner = findViewById(R.id.spinner)
        spinner.setSelection(i-1)
    }
}