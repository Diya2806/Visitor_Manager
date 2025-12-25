package com.diya.visitortracker

import Adapater.category_adapter
import Database.database
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class Category : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private val checkedItems = HashSet<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_category)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            val menu = findViewById<ImageView>(R.id.menu_cat)
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
                db.init(this@Category)
                val model = db.getUserById(id)

                val user = findViewById<TextView>(R.id.username)
                val name = findViewById<TextView>(R.id.catname)
                user.text = model?.getName() ?: "Guest"
                name.text = "Hello " + (model?.getName() ?: "Guest")
            }
            logout.setOnClickListener(){
                val session = getSharedPreferences("UserId", Context.MODE_PRIVATE)
                val editor = session.edit()
                editor.clear()
                editor.apply()

                val loginIntent = Intent(this@Category, Login_Page::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(loginIntent)
                finish()
            }

            val addcategory = findViewById<EditText>(R.id.category_add)
            val catbtn = findViewById<Button>(R.id.category_btn)
            val catlist = findViewById<ListView>(R.id.catlist)
            val catdelete = findViewById<Button>(R.id.category_delete_btn)
            var db = database();
            db.init(this@Category)

            fun loadCategoryList() {
                val categoryList = db.getcatdata()
                val adapter = category_adapter(this, R.layout.custom_cat_list, categoryList, checkedItems)
                catlist.adapter = adapter
            }

            loadCategoryList()

            catbtn.setOnClickListener(){
                var txtcat = addcategory.text.toString()

                if(txtcat.isEmpty()){
                    Toast.makeText(this, "Please enter a category", Toast.LENGTH_SHORT).show()
                }
                else {
                    db.InsertCat(txtcat);
                    Toast.makeText(this, "Category Added", Toast.LENGTH_SHORT).show()
                    addcategory.setText("")
                    loadCategoryList()

                }

            }
            catdelete.setOnClickListener(){
                if (checkedItems.isEmpty()) {
                    Toast.makeText(this, "Please select categories to delete", Toast.LENGTH_SHORT).show()
                } else {
                    for (catId in checkedItems) {
                        db.DeleteCatTbl(catId)

                    }
                    checkedItems.clear()
                    Toast.makeText(this, "Selected categories deleted", Toast.LENGTH_SHORT).show()
                    loadCategoryList()
                }
            }

        }catch (e:Exception){
            println(e)
        }
    }
}