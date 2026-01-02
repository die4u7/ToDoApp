package com.fit2081.todoapp.ui.theme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.fit2081.todoapp.ui.theme.viewModel.TodoViewModel

@Composable
fun TodoScreen(viewModel: TodoViewModel) {


    val todos by viewModel.todos.collectAsState()
    val query by viewModel.query.collectAsState()


    val categories = listOf("Work", "Study", "Life")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var expanded by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        TextField(
            value = query,
            onValueChange = { viewModel.updateQuery(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search todos") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Title (required)") }
        )

        Spacer(modifier = Modifier.height(8.dp))


        TextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Description (optional)") }
        )

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box {
                Button(onClick = { expanded = true }) {
                    Text(selectedCategory)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.addTodo(
                            title = title,
                            description = description.takeIf { it.isNotBlank() },
                            category = selectedCategory
                        )
                        title = ""
                        description = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(todos) { todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = todo.isCompleted,
                        onCheckedChange = {
                            viewModel.toggleTodo(todo.id)
                        }
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {

                        Text(
                            text = todo.title,
                            style = if (todo.isCompleted) {
                                LocalTextStyle.current.copy(
                                    textDecoration = TextDecoration.LineThrough
                                )
                            } else {
                                LocalTextStyle.current
                            }
                        )

                        todo.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = todo.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = { viewModel.deleteTodo(todo.id) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete todo"
                        )
                    }
                }
            }
        }
    }
}
