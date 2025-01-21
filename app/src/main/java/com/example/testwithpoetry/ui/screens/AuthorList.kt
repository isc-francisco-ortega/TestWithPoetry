package com.example.testwithpoetry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testwithpoetry.ui.viewmodels.AuthorListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorList(
    navigateToAuthorDetail: (String) -> Unit,
    authorListViewModel: AuthorListViewModel
) {
    val authors by authorListViewModel.authors.collectAsState()
    val user by authorListViewModel.user.collectAsState()
    val favorites by authorListViewModel.favoritesAuthors.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Welcome ${user?.name}")
                    }
                }
            )
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(authors) { author ->
                AuthorItem(
                    author,
                    favoriteOnClick = {
                        if (!favorites.contains(author)) {
                            authorListViewModel.insertFavoriteAuthor(author) { success ->
                                authorListViewModel.loadFavoritesAuthors()
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "$author added to favorites",
                                        actionLabel = "Close",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        } else {
                            authorListViewModel.deleteFavoriteAuthor(author) { success ->
                                authorListViewModel.loadFavoritesAuthors()
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "$author deleted from favorites",
                                        actionLabel = "Close",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }
                    },
                    onClick = {
                        navigateToAuthorDetail(author)
                    },
                    favorites.contains(author)
                )
            }
        }

    }

}

@Composable
fun AuthorItem(
    author: String,
    favoriteOnClick: () -> Unit,
    onClick: () -> Unit,
    isFavorite: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            //painter = painterResource(id = if(isFavorite) R.drawable.outlined_star else R.drawable.ic_launcher_foreground),
            contentDescription = "Favorites",
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable { favoriteOnClick() }

        )
        Text(
            text = author,
            style = MaterialTheme.typography.titleLarge
        )
    }
}