package com.example.bailaappandroid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateMapOf


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogScreen() {

    val coroutineScope = rememberCoroutineScope()

    val lazyGridState = rememberLazyGridState()
    val lazyRowState = rememberLazyListState()

    val groupedOutfits = remember {
        FakeData.outfits.groupBy { it.collectionId }
    }

    val headerIndexes = remember { mutableStateMapOf<Long, Int>() }

    var activeCollectionId by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(lazyGridState.layoutInfo.visibleItemsInfo) {
        lazyGridState.layoutInfo.visibleItemsInfo.forEach { item ->
            val key = item.key
            if (key is String && key.startsWith("header_")) {
                val id = key.removePrefix("header_").toLong()
                headerIndexes[id] = item.index
            }
        }
    }

    LaunchedEffect(lazyGridState.layoutInfo.visibleItemsInfo) {
        val firstHeader = lazyGridState.layoutInfo.visibleItemsInfo
            .firstOrNull { it.key is String && (it.key as String).startsWith("header_") }

        firstHeader?.let {
            val id = (it.key as String).removePrefix("header_").toLong()
            activeCollectionId = id
        }
    }

    LaunchedEffect(activeCollectionId) {
        activeCollectionId?.let { id ->
            val index = FakeData.collections.indexOfFirst { it.id == id }
            if (index != -1) {
                lazyRowState.animateScrollToItem(index)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        MainSearchBar()

        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {

            stickyHeader {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyRow(
                        state = lazyRowState,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = FakeData.collections,
                            key = { it.id }
                        ) { collection ->

                            CollectionChip(
                                collectionName = collection.name,
                                isActive = collection.id == activeCollectionId,
                                onClick = {
                                    coroutineScope.launch {
                                        headerIndexes[collection.id]?.let { index ->
                                            lazyGridState.animateScrollToItem(index)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }

            groupedOutfits.forEach { (collectionId, outfitsInCollection) ->

                item(
                    key = "header_$collectionId",
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    val collectionName = FakeData.collections
                        .find { it.id == collectionId }
                        ?.name ?: ""

                    Text(
                        text = collectionName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )
                }

                items(
                    items = outfitsInCollection,
                    key = { it.id }
                ) { outfit ->
                    OutfitCard(outfit = outfit)
                }
            }
        }
    }
}


@Composable
fun CollectionChip(
    collectionName: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (isActive) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Text(
        text = collectionName,
        color = textColor,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}


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
