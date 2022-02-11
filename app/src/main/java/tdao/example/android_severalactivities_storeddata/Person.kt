package tdao.example.android_severalactivities_storeddata

/**No5-2: Create a data class whose main purpose is to hold data
 * It will be used to streaming the json file
 * Check the integrated steps in Module build.gradle files
 * Next step is create assets folder and put the json into it*/
// data class to be used to stream json in and out
data class Person(var age:Int = 0, val name:String?=null) {
}