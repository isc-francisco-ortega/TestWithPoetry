package com.example.testwithpoetry.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testwithpoetry.ui.BottomNavigationBar


@Composable
fun AuthorDetailsScreen(
    authorName: String
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
                AuthorDetails(
                    authorName = authorName,
                    authorDetailsViewModel = hiltViewModel()
                )
            }
            composable("profile") {
                ProfileScreen(
                    profileViewModel = hiltViewModel()
                )
            }
        }
    }

}