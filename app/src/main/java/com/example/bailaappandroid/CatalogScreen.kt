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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogScreen() {
    val coroutineScope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()
    val lazyRowState = rememberLazyListState()

    val groupedOutfits = remember { FakeData.outfits.groupBy { it.collectionId } }
    val indexToCollectionIdMap = remember {
        val map = mutableMapOf<Int, Int>()
        var currentIndex = 1 // 0 - это stickyHeader
        groupedOutfits.forEach { (collectionId, outfits) ->
            // Индекс заголовка
            map[currentIndex] = collectionId.toInt()
            currentIndex++
            // Индексы карточек
            val numRows = (outfits.size + 1) / 2
            repeat(numRows) {
                map[currentIndex] = collectionId.toInt()
                currentIndex++
            }
        }
        map
    }

    val collectionIdToHeaderIndexMap = remember {
        val map = mutableMapOf<Long, Int>()
        var currentIndex = 1 // 0 - это stickyHeader
        groupedOutfits.forEach { (collectionId, outfits) ->
            map[collectionId] = currentIndex
            // +1 для заголовка, + (outfits.size + 1) / 2 для строк с карточками
            currentIndex += 1 + (outfits.size + 1) / 2
        }
        map
    }

    val activeCollectionId by remember {
        derivedStateOf {
            val firstVisibleItemIndex = lazyGridState.firstVisibleItemIndex
            // Находим ID коллекции, соответствующий текущему видимому индексу
            indexToCollectionIdMap[firstVisibleItemIndex]
        }
    }

    LaunchedEffect(activeCollectionId) {
        activeCollectionId?.let { id ->
            val activeIndex = FakeData.collections.indexOfFirst { it.id.toInt() == id }
            if (activeIndex != -1) {
                coroutineScope.launch {
                    lazyRowState.animateScrollToItem(activeIndex)
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        MainSearchBar()

        LazyVerticalGrid(
            state = lazyGridState, // Передаем состояние для отслеживания
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            stickyHeader {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyRow(
                        state = lazyRowState, // Передаем состояние для управления
                        contentPadding = PaddingValues(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(FakeData.collections) { collection ->
                            CollectionChip(
                                collectionName = collection.name,
                                // --- ШАГ 4: Передаем флаг активности в "чип" ---
                                isActive = collection.id == (activeCollectionId?.toLong() ?: -1),
                                onClick = {
                                    coroutineScope.launch {
                                        // Находим индекс заголовка по ID коллекции
                                        val headerIndex = collectionIdToHeaderIndexMap[collection.id]
                                        headerIndex?.let {
                                            // И просто прокручиваем к нему!
                                            lazyGridState.animateScrollToItem(it)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // Выводим сгруппированные данные
            groupedOutfits.forEach { (collectionId, outfitsInCollection) ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                    val collectionName = FakeData.collections.find { it.id.toLong() == collectionId }?.name ?: ""
                    Text(
                        text = collectionName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )
                }
                items(outfitsInCollection) { outfit ->
                    OutfitCard(outfit = outfit)
                }
            }
        }
    }
}

private fun createIndexToCollectionIdMap(groupedData: Map<Long, List<Outfit>>): Map<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    var currentIndex = 1 // Начинаем с 1, так как 0 - это stickyHeader

    groupedData.forEach { (collectionId, outfits) ->
        // Индекс заголовка коллекции
        map[currentIndex] = collectionId.toInt()
        currentIndex++

        // Индексы товаров в этой коллекции
        for (i in outfits.indices) {
            map[currentIndex] = collectionId.toInt()
            // Для товаров в сетке с 2 колонками, индекс увеличивается не на 1, а на 1/2.
            // Но для простоты и надежности, можно присваивать ID каждому элементу.
            if (i % 2 == 1) currentIndex++ // Увеличиваем индекс строки сетки после каждых 2-х товаров
        }
        // Если в коллекции нечетное число товаров, все равно нужно увеличить индекс
        if (outfits.size % 2 != 0) {
            currentIndex++
        }
    }
    return map
}

@Composable
fun CollectionChip(collectionName: String, isActive: Boolean, onClick: () -> Unit) {
    // Определяем цвет фона в зависимости от состояния
    val backgroundColor = if (isActive) {
        MaterialTheme.colorScheme.primary // Яркий цвет для активного
    } else {
        Color.LightGray.copy(alpha = 0.5f) // Стандартный для неактивного
    }

    // Определяем цвет текста
    val textColor = if (isActive) {
        MaterialTheme.colorScheme.onPrimary // Контрастный цвет для текста на primary
    } else {
        MaterialTheme.colorScheme.onSurface // Стандартный цвет текста
    }

    Text(
        text = collectionName,
        color = textColor,
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
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
