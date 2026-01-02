package com.fit2081.todoapp.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.todoapp.data.local.Todo
import com.fit2081.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch



class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun updateQuery(text: String) {
        _query.value = text
    }

    val todos = combine(
        repository.observeTodos(),
        query
    ) { todos, q ->
        if (q.isBlank()) {
            todos
        } else {
            todos.filter {
                it.title.contains(q, ignoreCase = true) ||
                        it.category.contains(q, ignoreCase = true)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun addTodo(title: String, category: String) {
        viewModelScope.launch {
            repository.add(
                Todo(
                    title = title,
                    category = category,
                    isCompleted = false
                )
            )
        }
    }

    fun toggleTodo(id: Int) {
        viewModelScope.launch {
            repository.toggle(id)
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}

