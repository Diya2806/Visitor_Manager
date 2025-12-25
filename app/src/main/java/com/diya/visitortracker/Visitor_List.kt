package com.diya.visitortracker

import Adapater.custom_adapter
import Database.database
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class Visitor_List : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_visitor_list)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            val menu = findViewById<ImageView>(R.id.menu_visitorlist)
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
                db.init(this@Visitor_List)
                val model = db.getUserById(id)


                val user = findViewById<TextView>(R.id.username)
                val name = findViewById<TextView>(R.id.listname)
                user.text = model?.getName() ?: "Guest"
                name.text = "Hello " + (model?.getName() ?: "Guest")
            }
            logout.setOnClickListener(){
                val session = getSharedPreferences("UserId", Context.MODE_PRIVATE)
                val editor = session.edit()
                editor.clear()
                editor.apply()

                val loginIntent = Intent(this@Visitor_List, Login_Page::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(loginIntent)
                finish()
            }

            val list = findViewById<ListView>(R.id.Visitor_List);
            val db = database()
            db.init(this@Visitor_List)
            val visitorData = db.getAllVisitors()

            val adapter = custom_adapter(this, R.layout.custom_list, visitorData)
            list.adapter = adapter


        }catch (e:Exception){
            println(e)
        }
    }
}