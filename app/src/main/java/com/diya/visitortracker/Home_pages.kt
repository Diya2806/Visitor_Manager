package com.diya.visitortracker

import Database.database
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class Home_pages : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_home_pages)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            val visitor = findViewById<CardView>(R.id.addvisitor_card)
            val visitorOut = findViewById<CardView>(R.id.visitor_out_card)
            val visitorList = findViewById<CardView>(R.id.dailyvisitor_card)
            val lanlord =  findViewById<CardView>(R.id.houseowner_card)
            val rent = findViewById<CardView>(R.id.rental_house_card)
            val menu = findViewById<ImageView>(R.id.menu)
            val Name = findViewById<TextView>(R.id.homename)
            drawerLayout = findViewById(R.id.drawer_layout)

            visitor.setOnClickListener(){
                val newvisitor = Intent(this@Home_pages,newVisitor::class.java)
                startActivity(newvisitor)
            }

            visitorOut.setOnClickListener(){
                val Visiorout = Intent(this@Home_pages,Visitor_out::class.java)
                startActivity(Visiorout)
            }
            visitorList.setOnClickListener(){
                val VisitorList = Intent(this@Home_pages,Visitor_List::class.java)
                startActivity(VisitorList)
            }
            lanlord.setOnClickListener(){
                val Landlord = Intent(this@Home_pages,Landlord::class.java)
                startActivity(Landlord)
            }
            rent.setOnClickListener(){
                val rental = Intent(this@Home_pages,Rental::class.java)
                startActivity(rental)
            }

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
                db.init(this@Home_pages)
                val model = db.getUserById(id)


                val user = findViewById<TextView>(R.id.username)
                user.text = model?.getName() ?: "Guest"
                Name.text = "Hello "+(model?.getName() ?: "Guest")
            }
            logout.setOnClickListener(){
                val session = getSharedPreferences("UserId", Context.MODE_PRIVATE)
                val editor = session.edit()
                editor.clear()
                editor.apply()

                val loginIntent = Intent(this@Home_pages, Login_Page::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(loginIntent)
                finish()
            }


        }
        catch (e:Exception){
            println(e)
        }
    }
}