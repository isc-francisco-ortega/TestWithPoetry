package com.example.testwithpoetry.ui.navigation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.testwithpoetry.domain.localModels.User
import com.example.testwithpoetry.ui.screens.AuthorDetailsScreen
import com.example.testwithpoetry.ui.screens.AuthorListScreen
import com.example.testwithpoetry.ui.screens.UserFormScreen
import com.example.testwithpoetry.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val userExist by mainViewModel.userExists
    val isLoading = userExist == null
    NavHost(
        navController = navController,
        startDestination = if (isLoading) LoadingScreen else EmptyScreen
    ) {
        composable<LoadingScreen> {
            LaunchedEffect(userExist) {
                if (userExist != null) {
                    if (userExist == true) {
                        navController.navigate(AuthorsList) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(UserForm) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
        composable<EmptyScreen> {
            CircularProgressIndicator()
        }
        composable<UserForm> {
            UserFormScreen(
                {
                    navController.navigate(AuthorsList){
                        popUpTo(UserForm) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                viewModel = hiltViewModel())
        }
        composable<AuthorsList> {
            AuthorListScreen(
                {name -> navController.navigate(ParameterAuthor(nameAuthor = name))},
                authorListViewModel = hiltViewModel()
            )
        }
        composable<ParameterAuthor> {
            val parameterAuthor = it.toRoute<ParameterAuthor>()
            AuthorDetailsScreen(
                authorName = parameterAuthor.nameAuthor
            )
        }
    }

}
