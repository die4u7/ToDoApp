package com.fit2081.todoapp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fit2081.todoapp.ui.theme.viewModel.TodoViewModel

@Composable
fun TodoScreen(viewModel: TodoViewModel) {

    val todos by viewModel.todos.collectAsState()
    var input by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Row {
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("New todo") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (input.isNotBlank()) {
                    viewModel.addTodo(input)
                    input = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(todos) { todo ->
                Text(
                    text = if (todo.isCompleted) "✔ ${todo.title}" else todo.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.toggleTodo(todo.id)
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}
