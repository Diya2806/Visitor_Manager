package com.diya.visitortracker

import Database.database
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class newVisitor : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_new_visitor)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            val addbtn = findViewById<Button>(R.id.addVisitorbtn)
            val name =findViewById<EditText>(R.id.name_visitor)
            val number = findViewById<EditText>(R.id.number_visitor)
            val block = findViewById<Spinner>(R.id.block)
            val blocknumber = findViewById<EditText>(R.id.Block_number)
            val vehical =findViewById<EditText>(R.id.vehical_number)
            val time = findViewById<EditText>(R.id.time_in)
            val vtype = findViewById<Spinner>(R.id.vtype)



            vehical.addTextChangedListener(object : TextWatcher {
                var isFormatting = false

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (isFormatting || s == null) return
                    isFormatting = true

                    val cleaned = s.toString().replace("[^A-Za-z0-9]".toRegex(), "").uppercase()

                    vehical.setText(cleaned)
                    vehical.setSelection(vehical.text.length)

                    val pattern = Regex("^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$")

                    if (cleaned.matches(pattern)) {
                        // Valid format → black text
                        vehical.setTextColor(resources.getColor(android.R.color.black))
                    } else {
                        // Invalid format → red text
                        vehical.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                    }
                    isFormatting = false

                }
            })


            val db = database()
            db.init(this@newVisitor)

            val categoryTypes = db.getCategoryTypes()
            val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryTypes)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            vtype.adapter = spinnerAdapter



            addbtn.setOnClickListener(){
                try {


                    val txtname = name.text.toString()
                    val intnumber = number.text.toString().toLongOrNull() ?: 0L
                    val Block = block.selectedItem.toString() ?:"null"
                    val Blocknumber = blocknumber.text.toString().toInt()
                    val Vnumber = vehical.text.toString()
                    val Time = time.text.toString()
                    val type = vtype.selectedItem.toString()

//
                    val pattern = Regex("^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$")
                    if (Vnumber.isNotEmpty() && !Vnumber.matches(pattern)) {
                        vehical.error = "Invalid vehicle format (e.g. GJ01AB1234)"
                        return@setOnClickListener
                    }
                    Toast.makeText(this,"Button Click",Toast.LENGTH_LONG).show()

//
                    val finalVehicle = if (Vnumber.isEmpty()) null else Vnumber

//                db.InsertNewVisitor(txtname,intnumber,Block,Blocknumber,type,finalVehicle ?:"Null",Time)
                    db.InsertNewVisitor(txtname,intnumber,Block,Blocknumber,type,finalVehicle?:"Null",Time)
//
                    val Home = Intent(this@newVisitor , Home_pages::class.java)
                    startActivity(Home)
                }
                catch (e:Exception)
                {
                    Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
                }

            }

            val menu = findViewById<ImageView>(R.id.menu_new_visitor)
            drawerLayout = findViewById(R.id.drawer_layout)
            menu.setOnClickListener(){
                drawerLayout.openDrawer(GravityCompat.START)
            }
            val dash = findViewById<TextView>(R.id.dash)
            val category = findViewById<TextView>(R.id.category)
            val logout = findViewById<TextView>(R.id.logout)

            dash.setOnClickListener {
                val home = Intent(this, Home_pages::class.java)
                startActivity(home)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            category.setOnClickListener {
                val category = Intent(this, Category::class.java)
                startActivity(category)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            val session = getSharedPreferences("UserId", Context.MODE_PRIVATE)
            val id = session.getString("ID", null)

            if (id != null) {
                val db = database()
                db.init(this@newVisitor)
                val model = db.getUserById(id)
                var name = findViewById<TextView>(R.id.visitorname)
                val user = findViewById<TextView>(R.id.username)
                user.text = model?.getName() ?: "Guest"
                name.text = "Hello " + (model?.getName() ?: "Guest")
            }
            logout.setOnClickListener(){
                val session = getSharedPreferences("UserId", Context.MODE_PRIVATE)
                val editor = session.edit()
                editor.clear()
                editor.apply()

                val loginIntent = Intent(this@newVisitor, Login_Page::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(loginIntent)
                finish()
            }


        }catch (e:Exception){
            println(e)
        }

    }
}