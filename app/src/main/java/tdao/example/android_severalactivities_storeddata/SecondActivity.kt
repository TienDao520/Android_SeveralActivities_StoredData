package tdao.example.android_severalactivities_storeddata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    fun onButtonClose(view: View) {
        /**No2-2: when we want to destroy 2nd activity and give values back to Main activity via companion object*/
        // send the data back to the mainActivity of number selected via the companion object
        // getting the spinnerValue (note as a string) and cast to an int and then set it to the spinValue in the companion object
        MainActivity.spinValue = Integer.parseInt(spinner.selectedItem.toString())
        // use finish to destroy this second activity so next time called won't have two instances of the second activity
        finish()
        // if you go back to the mainActivity by launching a new intent, the you will have multiple instances of your mainActivity
        //       val intent = Intent(this, MainActivity::class.java)
        //       startActivity(intent)
    }

}