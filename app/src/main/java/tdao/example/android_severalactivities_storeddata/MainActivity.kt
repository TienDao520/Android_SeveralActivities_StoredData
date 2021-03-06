package tdao.example.android_severalactivities_storeddata

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*

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

    /**No5-3: create collection*/
    //varialbe used to stream in the data from the JSON file.
    var people:List<Person> = listOf<Person>()

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


            /**No4-1: Save data to a file
             * To check the file remember to sync
             * It will be stored under data/data/<packagename>/files
             * */
            R.id.buttonSaveText -> {
                try {
                    // to save to file "test.txt" in data/data/packagename/File
                    val ofile: FileOutputStream = openFileOutput("test.txt", MODE_PRIVATE)
                    val osw = OutputStreamWriter(ofile)
                    osw.write(et.getText().toString())
                    osw.flush()
                    osw.close()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }
            }


            /**No4-2: Read data from a file
             * To check the file remember to sync
             * It will be stored under data/data/<packagename>/files
             * */
            R.id.buttonReadText -> {
                // read the file from the data/data/packagename
                // read the file from the data/data/packagename
                try {
                    // reading from data/data/packagename/File
                    val fin: FileInputStream = openFileInput("test.txt")
                    val isr = InputStreamReader(fin)
                    val inputBuffer = CharArray(100)
                    var str: String? = ""
                    var charRead: Int
                    while (isr.read(inputBuffer).also { charRead = it } > 0) {
                        val readString = String(inputBuffer, 0, charRead)
                        str += readString
                    }
                    et.setText(str)
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }
            }

            /**No5-5: Read data from json file
             * Json file was getting from the created assets folder
             * */
            R.id.buttonReadJSON -> {
                val jsonStringFromFile = getJSONDataFromAsset(this,"test.json")
                val listPersonType = object : TypeToken<List<Person>>() {}.type
                //pass the filename and object with Person class type for streaming data
                //Storing all get data to people variable
                people = Gson().fromJson(jsonStringFromFile, listPersonType)
                //use kotlin like Log.d to show information
                println(people)
            }

            /**No5-6: Save data to json file
             * Check the integrated steps in Module build.gradle files
             * file will be stored in to files -> test.json
             * */
            R.id.buttonSaveJSON -> {
                try {
                    // to save to JSON file "test.json" in data/data/packagename/File
                    //osw stand for output stream writer
                    val ofile: FileOutputStream = openFileOutput("test.json", MODE_PRIVATE)
                    val osw = OutputStreamWriter(ofile)
                    //Different with the test.txt file
                    var jsonList = Gson().toJson(people)
                    for(person in jsonList) {
                        osw.write(person.toString())
                    }
                    osw.flush()
                    osw.close()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }
            }



        }
    }

    /**No5-4: func get data from Json file
     * Json file was getting from the created assets folder
     * */
    fun getJSONDataFromAsset(context:Context, filename:String):String? {
        val jsonString:String
        try {
            // Use bufferedReader
            // Closable.use will automatically close the input at the end of execution
            // it.reader.readText()  is automatically
            jsonString = context.assets.open(filename).bufferedReader().use {
//            jsonString = this.assets.open(filename).bufferedReader().use {
                it.readText() }
        } catch(ioException:IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
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