package com.example.bailaappandroid.presentation.catalog.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchBar() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchHistory = listOf("search1", "bebebeb", "androidddd")

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = query,
        onQueryChange = { query = it },
        onSearch = { searchQuery ->
            println("Search: $searchQuery")
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("search...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (active) {
                IconButton(onClick = {
                    if (query.isNotEmpty()) {
                        query = ""
                    } else {
                        active = false
                    }
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Close Icon")
                }
            }
        }
    ) {
        searchHistory.forEach { historyItem ->
            ListItem(
                headlineContent = { Text(historyItem) },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                modifier = Modifier
                    .clickable {
                        query = historyItem
                        active = false
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
