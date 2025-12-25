package com.diya.visitortracker

import Database.database
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Create_Account_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_create_account_page)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
                val login = findViewById<TextView>(R.id.login_txt)
                val name = findViewById<EditText>(R.id.name_ca)
                val number = findViewById<EditText>(R.id.number_ca)
                val email = findViewById<EditText>(R.id.Email_ca)
                val male =findViewById<RadioButton>(R.id.male_rbtn)
                val female = findViewById<RadioButton>(R.id.female_rbtn)
                val socitey = findViewById<EditText>(R.id.socitey_ca)
                val pass = findViewById<EditText>(R.id.pass)
                val create = findViewById<Button>(R.id.create_btn)

//            database
                val db = database()
                db.init(this@Create_Account_page)


            login.setOnClickListener(){
                val Login = Intent(this@Create_Account_page,Login_Page::class.java)
                startActivity(Login)
            }
            create.setOnClickListener(){

                val nameText = name.text.toString()
                val numberText = number.text.toString().toInt()
                val emailText = email.text.toString()
                val sociteyText = socitey.text.toString()
                val passtext = pass.text.toString()

                val gender = when {
                    male.isChecked -> "Male"
                    female.isChecked -> "Female"
                    else -> ""
                }
                db.insertCAData(nameText,numberText,emailText,gender,sociteyText,passtext)
                val Home = Intent(this@Create_Account_page,Home_pages::class.java)
                startActivity(Home)

            }
        }catch (e:Exception){
            println(e)
        }
    }
}