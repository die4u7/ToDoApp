package com.fit2081.todoapp

interface todoRepository {
    fun getTodos(): Flow<List<Todo>>
}