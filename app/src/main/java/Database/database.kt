package Database

import Model.category_list
import Model.custom_list
import Model.model_id
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class database {
 lateinit var db : SQLiteDatabase
 lateinit var ds : Cursor
 private lateinit var context : Context

   fun init(context: Context) {
    this.context = context
   }

   fun Mycorn(){
    db = context.openOrCreateDatabase("VisitorData",Context.MODE_PRIVATE,null);
   }
  fun CreateAccountTbl(){
    db.execSQL("CREATE TABLE IF NOT EXISTS CaTbl(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Name varchar , Number Integer ,mail varchar , Gender varchar , Socitey varchar , Password Varchar)")
  }

    fun insertCAData(name: String, number: Int, email: String, gender: String, society: String, Password:String) {
        val query = "INSERT INTO CaTbl (Name , Number , mail,Gender,Socitey,Password) VALUES (?, ?, ?, ?, ?, ?)"
        Mycorn()
        CreateAccountTbl()
        db.execSQL(query, arrayOf(name,  number.toString(), email, gender , society , Password))
        db.close()
    }
    fun login(email: String, password: String): Int {
        return try {
            val query = "SELECT * FROM CaTbl WHERE mail = ? AND Password = ?"
            Mycorn()
            ds = db.rawQuery(query, arrayOf(email, password))
            val id = if (ds.moveToFirst()) {
                ds.getInt(0) // Assuming ID is the first column
            } else {
                0
            }
            ds.close()
            db.close()
            id
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
    fun getUserById(id: String): model_id? {
        Mycorn()
        CreateAccountTbl()
        val query = "SELECT * FROM CaTbl WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(id))

        var model: model_id? = null
        try {
            if (cursor.moveToFirst()) {
                model = model_id().apply {
                    setId(cursor.getInt(0))
                    setName(cursor.getString(1))

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return model
    }

    fun NewVisitoreTbl(){
        Mycorn()
        db.execSQL("CREATE TABLE IF NOT EXISTS NewVisitorTbl(vid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,Vname VARCHAR,Vmoblie LONG,Block VARCHAR,House INTEGER,Vtype VARCHAR,Vehicalnumber VARCHAR,TimeIn TIME,TimeOut TIME ,EntryDate Datetime)")
    }
    fun InsertNewVisitor(name: String , vmoblie:Long , Block:String , House:Int ,Vtype:String ,Vehicalnumber:String, Time:String){
        Mycorn()
        NewVisitoreTbl()
        val query = "INSERT INTO NewVisitorTbl (Vname , Vmoblie , Block ,House,Vtype,Vehicalnumber,TimeIn,EntryDate) VALUES (?, ?, ?, ?, ?, ?, ?,DATE('now'))"
        db.execSQL(query, arrayOf(name,vmoblie,Block,House,Vtype,Vehicalnumber,Time))
        db.close()
    }
//Add name
fun getAllVisitorNames(): ArrayList<String> {
    Mycorn()
    val visitorNames = ArrayList<String>()

    val cursor = db.rawQuery("SELECT Vname FROM NewVisitorTbl", null)

    if (cursor.moveToFirst()) {
        do {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("Vname"))
            if (!visitorNames.contains(name)) {
                visitorNames.add(name)
            }
        } while (cursor.moveToNext())
    }

    cursor.close()
    db.close()
    return visitorNames
}
    fun updateOutTime(visitorName: String, timeOut: String) {
        Mycorn()
        val query = "UPDATE NewVisitorTbl SET TimeOut = ? WHERE Vname = ? AND TimeOut IS NULL"
        db.execSQL(query, arrayOf(timeOut, visitorName))
        db.close()
    }
    fun getAllVisitors(): ArrayList<custom_list> {

        Mycorn()
        val list = ArrayList<custom_list>()

        val query = "SELECT * FROM NewVisitorTbl ORDER BY EntryDate DESC"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val visitor = custom_list(
                    name = cursor.getString(cursor.getColumnIndexOrThrow("Vname")),
                    number = cursor.getInt(cursor.getColumnIndexOrThrow("Vmoblie")),
                    vehical = cursor.getString(cursor.getColumnIndexOrThrow("Vehicalnumber")),
                    Type = cursor.getString(cursor.getColumnIndexOrThrow("Vtype")),
                    TimeIn = cursor.getString(cursor.getColumnIndexOrThrow("TimeIn")),
                    TimeOut = cursor.getString(cursor.getColumnIndexOrThrow("TimeOut")),
                    entryDate = cursor.getString(cursor.getColumnIndexOrThrow("EntryDate"))

                )
                list.add(visitor)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }
    fun CategoryTbl(){
        db.execSQL("CREATE TABLE IF NOT EXISTS CategoryTbl(cid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,categoryType VARCHAR,EntryDate Datetime)")
    }
    fun InsertCat(Type:String){
        Mycorn()
        CategoryTbl()
        val qurey = "INSERT INTO CategoryTbl(categoryType,EntryDate) VALUES(?,DATE('now'))"
        db.execSQL(qurey, arrayOf(Type))
    }
    fun getcatdata(): ArrayList<category_list> {
        Mycorn()
//        CategoryTbl()
        val list = ArrayList<category_list>()
        val query = "SELECT cid, categoryType FROM CategoryTbl ORDER BY EntryDate DESC"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val model = category_list(
                    Id = cursor.getInt(cursor.getColumnIndexOrThrow("cid")) ,
                    Type = cursor.getString(cursor.getColumnIndexOrThrow("categoryType"))
                )

                list.add(model)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    fun DeleteCatTbl(cid:Int){
        Mycorn()
        val qurey = "DELETE FROM CategoryTbl WHERE cid=?"
        db.execSQL(qurey, arrayOf(cid))
        db.close()

    }

    fun getCategoryTypes(): ArrayList<String> {

        Mycorn()
        CategoryTbl()
        val list = ArrayList<String>()
        val cursor = db.rawQuery("SELECT categoryType FROM CategoryTbl ORDER BY EntryDate DESC", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndexOrThrow("categoryType")))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }


}