package com.fit2081.todoapp.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getTodos():Flow<List<Todo>>

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteTodo(id: Int)

    @Query("UPDATE todo SET isCompleted = NOT isCompleted WHERE id = :id")
    suspend fun updateTodo(id: Int): Int
}