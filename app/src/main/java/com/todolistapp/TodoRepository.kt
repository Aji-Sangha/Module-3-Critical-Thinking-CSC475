package com.todolistapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class TodoRepository(context: Context) {
    private val dbHelper = TodoDatabaseHelper(context)

    fun addTask(task: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TodoDatabaseHelper.COLUMN_TASK, task)
            put(TodoDatabaseHelper.COLUMN_COMPLETED, 0)
        }
        db.insert(TodoDatabaseHelper.TABLE_NAME, null, values)
    }

    fun deleteTask(id: Long) {
        val db = dbHelper.writableDatabase
        db.delete(TodoDatabaseHelper.TABLE_NAME, "${TodoDatabaseHelper.COLUMN_ID}=?", arrayOf(id.toString()))
    }

    fun markTaskAsCompleted(id: Long) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TodoDatabaseHelper.COLUMN_COMPLETED, 1)
        }
        db.update(TodoDatabaseHelper.TABLE_NAME, values, "${TodoDatabaseHelper.COLUMN_ID}=?", arrayOf(id.toString()))
    }

    fun getAllTasks(): List<TodoItem> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            TodoDatabaseHelper.TABLE_NAME,
            null, null, null, null, null, null
        )
        val tasks = mutableListOf<TodoItem>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_ID))
            val task = cursor.getString(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_TASK))
            val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_COMPLETED)) == 1
            tasks.add(TodoItem(id, task, isCompleted))
        }
        cursor.close()
        return tasks
    }
}
