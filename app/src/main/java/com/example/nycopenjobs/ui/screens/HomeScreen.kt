package com.example.nycopenjobs.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.nycopenjobs.R
import com.example.nycopenjobs.model.JobPost
import com.example.nycopenjobs.util.LoadingSpinner
import com.example.nycopenjobs.util.ToastMessage
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
) {
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showFavorites by remember { mutableStateOf(false) }

    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopBar(
                isSearching = isSearching,
                searchQuery = searchQuery,
                onSearchIconClick = { isSearching = !isSearching },
                onSearchQueryChange = { searchQuery = it }
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController,
            onFavoritesClick = { showFavorites = !showFavorites }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        // Main content
        Column(modifier = modifier.padding(paddingValues)) {
            when (uiState) {
                is HomeScreenUIState.Loading -> LoadingSpinner()
                is HomeScreenUIState.Success -> {
                    val filteredJobs = uiState.data.filter {
                        it.businessTitle.contains(searchQuery, ignoreCase = true) &&
                                (!showFavorites || it.isFavorite)
                    }

                    JobListings(
                        jobs = filteredJobs,
                        loadMoreData = {
                            viewModel.getJobPostings()
                        },
                        updateScrollPosition = { scrollPosition ->
                            viewModel.setScrollPosition(scrollPosition)
                        },
                        scrollPosition = viewModel.getScrollPosition(),
                        modifier = modifier.weight(1f),
                        navController = navController
                    )
                }
                is HomeScreenUIState.Error -> ToastMessage(stringResource(R.string.job_listing_not_available_at_this_time))
                else -> ToastMessage(stringResource(R.string.job_listing_loaded))
            }
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
fun JobListings(
    jobs: List<JobPost>,
    loadMoreData: () -> Unit,
    updateScrollPosition: (Int) -> Unit,
    scrollPosition: Int,
    modifier: Modifier,
    navController: NavHostController
) {
    val firstVisibleIndex = if (scrollPosition > jobs.size) 0 else scrollPosition
    val listState: LazyListState = rememberLazyListState(firstVisibleIndex)

    // LazyColumn to display list of jobs
    LazyColumn(modifier = modifier, state = listState) {
        items(jobs) { jobPost ->
            JobItem(
                jobPost = jobPost,
                navController = navController
            )
            Divider()
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .debounce(timeoutMillis = 500L)
            .collect { lastVisibleIndex ->
                updateScrollPosition(listState.firstVisibleItemIndex)
                if (lastVisibleIndex != null && lastVisibleIndex >= jobs.size - 1) {
                    loadMoreData()
                }
            }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    isSearching: Boolean,
    searchQuery: String,
    onSearchIconClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    TopAppBar(
        title = {
            if (isSearching) {
                TextField(
                    value = searchQuery,  // searchQuery
                    onValueChange = { newQuery ->
                        Log.d("TopBar", "Search query changed: $newQuery")
                        onSearchQueryChange(newQuery)
                    },
                    placeholder = { Text("Search jobs...") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text("NYC Open Jobs")
            }
        },
        actions = {
            IconButton(onClick = onSearchIconClick) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFADD8E6),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}


@Composable
fun BottomNavigation(
    navController: NavHostController,
    onFavoritesClick: () -> Unit
) {
    BottomAppBar {
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
        }
        IconButton(onClick = { onFavoritesClick() }) { // Toggle favorites view
            Icon(Icons.Filled.Favorite, contentDescription = "Favorites")
        }
    }
}

