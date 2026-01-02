package com.fit2081.todoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class Todo (
    @PrimaryKey(autoGenerate =  true)
    val id : Int = 0,
    val title : String,
    val isCompleted : Boolean = false,
)
