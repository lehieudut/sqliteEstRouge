package com.hieulele.sqliteestrouge.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.hieulele.sqliteestrouge.model.City
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.sql.SQLException

/**
 * Created by Le Hieu on 6/14/19.
 *
 */
class DatabaseHandler(val context: Context) :
    SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {
    private val DB_Path = context.applicationInfo.dataDir + "/databases/"
    private val DB_Name = "utopia_cities.db"
    private var myDatabase: SQLiteDatabase? = null

    init {
        val dbexist = checkDatabase()
        if (dbexist) {
            println("Database exists")
            openDatabse()
        } else {
            println("Database doesn't exist")
            checkAndCopyDatabase()
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }


    private fun checkDatabase(): Boolean {
        var checkDB: SQLiteDatabase? = null

        try {
            val myPath = DB_Path + DB_Name
            val file = File(myPath)
            if (!file.exists())
                return false
            else
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLException) {

        }

        checkDB?.close()
        return if (checkDB != null) true else false
    }

    private fun copyDatabase() {
        try {
            val myInput = context.getAssets().open(DB_Name)
            val outFileName = DB_Path + DB_Name
            val myOutput = FileOutputStream(outFileName)
            val buffer = ByteArray(1024)
            var length = 0

            while ({ length = myInput.read(buffer);length }() > 0) {
                myOutput.write(buffer, 0, length)
            }
            myOutput.flush()
            myInput.close()
            myOutput.close()
            openDatabse()
        } catch (e: Exception) {

        }
    }

    @Throws(SQLException::class)
    fun openDatabse() {
        val myPath = DB_Path + DB_Name
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Throws(SQLException::class)
    fun ExeSQLData(sql: String) {
        myDatabase?.execSQL(sql)
    }

    @Throws(SQLException::class)
    fun queryData(query: String): Cursor {
        return myDatabase!!.rawQuery(query, null)
    }

    @Synchronized
    override fun close() {
        if (myDatabase != null)
            myDatabase!!.close()
        super.close()
    }

    fun checkAndCopyDatabase() {
        val dbExist = checkDatabase()
        if (dbExist) {
            Log.d("TAG", "DataBase Already Exists")
        } else {
            this.readableDatabase
            try {
                copyDatabase()
            } catch (e: IOException) {
                Log.d("Tag", "Error Copying Database")
            }
        }
    }

    fun getListCity(page: Int): ArrayList<City>? {
        try {
            val cityList = ArrayList<City>()
            val selectQuery = "SELECT  * FROM $TABLE_NAME LIMIT ${page * 30},30"
            val cursor = queryData(selectQuery)
            if (cursor.moveToFirst()) {
                do {
                    val city = City()
                    city.id = cursor.getString(cursor.getColumnIndex(ID))
                    city.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
                    city.city = cursor.getString(cursor.getColumnIndex(CITY))
                    city.population = cursor.getInt(cursor.getColumnIndex(POPULATION))
                    cityList.add(city)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return cityList
        } catch (e: Exception) {
            return null
        }
    }

    fun getTotalCities(): Int{
            val selectQuery = "SELECT  * FROM $TABLE_NAME"
            val cursor = queryData(selectQuery)
            val count = cursor.count
            cursor.close()
            return count
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "ListCity"
        private val TABLE_NAME = "cities"
        private val ID = "id"
        private val COUNTRY = "country"
        private val CITY = "city"
        private val POPULATION = "population"
    }
}