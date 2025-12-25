package com.diya.visitortracker

import Database.database
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

class Visitor_out : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_visitor_out)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            val name = findViewById<Spinner>(R.id.name_out)
            val time = findViewById<EditText>(R.id.time_out)
            val btn = findViewById<Button>(R.id.timeoutbtn)
            val db = database()
            db.init(this@Visitor_out)
            val visitornames = db.getAllVisitorNames()

            visitornames.add(0, "Select Visitor Name")


            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, visitornames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            name.adapter = adapter
            name.setSelection(0)
            btn.setOnClickListener(){

                val names =name.selectedItem.toString()
                val times = time.text.toString()

                if (names == "Select Visitor Name") {
                    Toast.makeText(this, "Please select a visitor name", Toast.LENGTH_SHORT).show()
                } else if (times.isEmpty()) {
                    Toast.makeText(this, "Please enter out time", Toast.LENGTH_SHORT).show()
                } else {
                    db.updateOutTime(names, times)
                    Toast.makeText(this, "Out time updated successfully", Toast.LENGTH_SHORT).show()
                    time.text.clear()
                    name.setSelection(0)
                }
            }


            val menu = findViewById<ImageView>(R.id.menu_out_visitor)
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
                db.init(this@Visitor_out)
                val model = db.getUserById(id)


                val user = findViewById<TextView>(R.id.username)
                var name = findViewById<TextView>(R.id.outname)
                user.text = model?.getName() ?: "Guest"
                name.text = "Hello " + (model?.getName() ?: "Guest")
            }
            logout.setOnClickListener(){
                val session = getSharedPreferences("UserId", Context.MODE_PRIVATE)
                val editor = session.edit()
                editor.clear()
                editor.apply()

                val loginIntent = Intent(this@Visitor_out, Login_Page::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(loginIntent)
                finish()
            }
        }catch (e:Exception){
            println(e)
        }
    }
}