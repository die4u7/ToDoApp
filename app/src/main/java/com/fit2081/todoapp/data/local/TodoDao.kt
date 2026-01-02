package com.fit2081.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getTodos():Flow<List<Todo>>

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Query("DELETE FROM Todo WHERE id = :id")
    suspend fun deleteTodo(id: Int)

    @Query("UPDATE Todo SET isCompleted = NOT isCompleted WHERE id = :id")
    suspend fun updateTodo(id: Int): Int
}