package com.fit2081.todoapp.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.todoapp.data.local.Todo
import com.fit2081.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {
    val todos = repository.observeTodos()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    fun addTodo(title: String) {
        viewModelScope.launch {
            repository.add(Todo(title = title))
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
