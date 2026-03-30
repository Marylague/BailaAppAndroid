package com.example.bailaappandroid.presentation.catalog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bailaappandroid.presentation.catalog.components.OutfitCard
import com.example.bailaappandroid.presentation.catalog.components.SnowfallAnimation
import com.example.bailaappandroid.presentation.catalog.components.CollectionChip
import com.example.bailaappandroid.presentation.catalog.components.MainSearchBar


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogScreen(viewModel: CatalogViewModel = viewModel()) {

    val collections = viewModel.collections
    val outfits = viewModel.outfits
    val coroutineScope = rememberCoroutineScope()

    val lazyGridState = rememberLazyGridState()
    val lazyRowState = rememberLazyListState()

    val groupedOutfits = remember(outfits) {
        outfits.groupBy { it.collectionId }
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
            val index = collections.indexOfFirst { it.id == id }
            if (index != -1) {
                lazyRowState.animateScrollToItem(index)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

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
                                items = collections,
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
                        val collectionName = collections
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
        SnowfallAnimation()
    }
}
