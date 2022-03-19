package com.pgpfse.hariTestApp.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.pgpfse.hariTestApp.Bean.ProdDB

private val DB_NAME = "myprod.db"
private val DB_VERSION = 2
public class LoginDBAdapter(context: Context)
    :SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    val TAG = LoginDBAdapter::class.java.simpleName

    private val TABLE_LOGIN = "medical_prodcuts"
    private val COLUMN_ID = "prod_id"
    private val COLUMN_productNAME = "medical1"
    private val COLUMN_PASSWORD = "medical2"

    private val createTable = ("CREATE TABLE " + TABLE_LOGIN + "" +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_productNAME + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL)")
    // drop table sql query
    private val dropTable = "DROP TABLE IF EXISTS $TABLE_LOGIN"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTable)
        Log.v(TAG, "Table created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(dropTable)//Drop product Table if exist
        Log.v(TAG, "Table Dropped")
        onCreate(db)// Create tables again
    }

    fun addProduct(prod_db: ProdDB) : Int {
        val returnCheck: Int
        val db = this.writableDatabase

        val values = ContentValues()
            values.put(COLUMN_productNAME, prod_db.medicine1)
            values.put(COLUMN_PASSWORD, prod_db.medicine2)
        // Inserting Row
        returnCheck = db.insert(TABLE_LOGIN, null, values).toInt()
        db.close()
        return returnCheck
    }
    fun fetchEquivalentProduct(prod_ID: String): ProdDB{

        // array of columns to fetch
        val columns = arrayOf(COLUMN_ID, COLUMN_productNAME, COLUMN_PASSWORD)
        // selection argument
        val selectionArgs = arrayOf(prod_ID)
        val db = this.readableDatabase
        println(prod_ID)
        // query the product table
        val cursor = db.query(
            TABLE_LOGIN,                //Table to query
            columns,                    //columns to return
            "$COLUMN_productNAME = ?",  //columns for the WHERE clause
            selectionArgs,             //The values for the WHERE clause
            null,               //group the rows
            null,                //filter by row groups
            null                //The sort order
        )
        var product = ProdDB(id=-1,medicine1 = "",medicine2 = "")
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()

            product = ProdDB(id = cursor.getString(cursor.getColumnIndex(COLUMN_ID)).toInt(),
                medicine1 = cursor.getString(cursor.getColumnIndex(COLUMN_productNAME)),
                medicine2 = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)))
            cursor.close()
            db.close()

            }
        return product

    }

    fun removeProduct(prod_ID: String): Int {
        val db = this.writableDatabase
        var returnCheck: Int = -1
        // delete product record by id
        returnCheck = db.delete(TABLE_LOGIN, "$COLUMN_productNAME = ?", arrayOf(prod_ID))
        db.close()
        return returnCheck
    }
}