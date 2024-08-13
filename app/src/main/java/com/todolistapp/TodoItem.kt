package com.todolistapp

data class TodoItem(
    val id: Long = 0,
    val task: String,
    val isCompleted: Boolean = false
)
