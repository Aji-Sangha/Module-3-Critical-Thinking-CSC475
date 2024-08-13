package com.todolistapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TASK TEXT, $COLUMN_COMPLETED INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "todo"
        const val COLUMN_ID = "id"
        const val COLUMN_TASK = "task"
        const val COLUMN_COMPLETED = "completed"
    }
}
