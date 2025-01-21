package com.example.testwithpoetry.ui.screens

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testwithpoetry.ui.BottomNavigationBar
import com.example.testwithpoetry.ui.viewmodels.AuthorListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorListScreen(
    navigateToAuthorDetail: (String)->Unit,
    authorListViewModel: AuthorListViewModel,
){
    val navController = rememberNavController()
    Scaffold (
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "authors",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("authors") {
                AuthorList(
                    navigateToAuthorDetail = navigateToAuthorDetail,
                    authorListViewModel = authorListViewModel)
            }
            composable("profile") {
                ProfileScreen(
                    profileViewModel = hiltViewModel()
                )
            }
        }
    }
}


