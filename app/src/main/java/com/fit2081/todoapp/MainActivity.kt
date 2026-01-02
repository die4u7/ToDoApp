package com.fit2081.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.fit2081.todoapp.data.local.TodoDatabase
import com.fit2081.todoapp.data.repository.TodoRepository
import com.fit2081.todoapp.ui.theme.TodoScreen
import com.fit2081.todoapp.ui.theme.viewModel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo.db"
        ).build()

        val repository = TodoRepository(db.todoDao())
        val viewModel = TodoViewModel(repository)

        setContent {
            TodoScreen(viewModel)
        }
    }
}
