package com.fit2081.todoapp.data.repository

import com.fit2081.todoapp.data.local.Todo
import com.fit2081.todoapp.data.local.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val dao: TodoDao
) {
    fun observeTodos(): Flow<List<Todo>> =
        dao.getTodos()

    suspend fun add(todo: Todo) =
        dao.insertTodo(todo)

    suspend fun delete(id: Int) =
        dao.deleteTodo(id)

    suspend fun toggle(id: Int) =
        dao.updateTodo(id)
}