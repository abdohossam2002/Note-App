package com.example.mynotesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager{
     val dpName="MyNotes"
     val dpNotesTable="Notes"
     val colID="ID"
     val colTitle="Title"
     val colDes="Description"
     val dpVersion=1;

     val sqlCreateTable="CREATE TABLE IF NOT EXISTS "+ dpNotesTable +" ("+ colID +" INTEGER PRIMARY KEY,"+
             colTitle + " TEXT, "+ colDes +" TEXT);"
     var sqlDB:SQLiteDatabase?=null

    constructor(context: Context){
        var dp=DatabaseHelperNotes(context)
        sqlDB=dp.writableDatabase
    }
    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context:Context?=null
        constructor(context: Context):super(context,dpName,null,dpVersion){
        this.context=context
    }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(context,"Data Base is Created" , Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dpNotesTable")
        }
    }

    fun insertNote(values: ContentValues):Long{
        val id = sqlDB!!.insert(dpNotesTable,"",values)
        return id
    }

    fun query(projection:Array<String>, selection:String, selectionArgs:Array<String>, sortOrder:String):Cursor{
        val db = SQLiteQueryBuilder()
        db.tables = dpNotesTable
        val cursor = db.query(sqlDB,projection,selection,selectionArgs,null,null,sortOrder)
        return cursor
    }
    fun delete(selection:String,selectionArgs:Array<String>):Int{
        val count = sqlDB!!.delete(dpNotesTable,selection,selectionArgs)
        return count
    }

    fun update(values: ContentValues,selection: String,selectionArgs: Array<String>):Int{
        val count = sqlDB!!.update(dpNotesTable,values,selection,selectionArgs)
        return count
    }
 }
