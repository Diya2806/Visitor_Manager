package com.diya.visitortracker

import Database.database
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login_Page : AppCompatActivity() {

    private val sessionKey = "UserId"
    private lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        try {

            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_login_page)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            val fp = findViewById<TextView>(R.id.forgot_pass)
            val btn = findViewById<Button>(R.id.login_btn)
            val ca= findViewById<TextView>(R.id.Create_acc)
            val mail = findViewById<EditText>(R.id.user_input)
            val pass =findViewById<EditText>(R.id.pass_input)
//          Session
            session = getSharedPreferences(sessionKey, Context.MODE_PRIVATE)
//            database conection
            val db = database()
            db.init(this@Login_Page)

            fp.setOnClickListener(){
                val intent = Intent(this@Login_Page, Forgot_password::class.java)
                startActivity(intent)
            }
            btn.setOnClickListener(){
                val mailtext = mail.text.toString()
                val passtext = pass.text.toString()

                val id = db.login(mailtext,passtext)

                if(id>0){
                    btn.isEnabled = true
                    btn.backgroundTintList = getColorStateList(R.color.dar_blue)

                    val editor = getSharedPreferences("UserId", MODE_PRIVATE).edit()
                    editor.putString("ID", id.toString()) // Use the actual ID from database
                    editor.apply()

                    val home = Intent(this@Login_Page , Home_pages::class.java)
                    startActivity(home)


                } else{
                   // btn.isEnabled = false
                    Toast.makeText(this, "Email or Mobile no ir Wrong.", Toast.LENGTH_SHORT).show()
                    btn.backgroundTintList = getColorStateList(R.color.red)
                }

            }
            ca.setOnClickListener(){
                val create = Intent(this@Login_Page , Create_Account_page::class.java)
                startActivity(create)
            }
        }catch (e:Exception){
            println(e)
        }
    }
}