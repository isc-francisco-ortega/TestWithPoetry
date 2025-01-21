package com.example.testwithpoetry.ui

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Authors", Icons.Default.List, "authors"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    val primaryColor = MaterialTheme.colorScheme.primary

    NavigationBar(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ){
        val currentDestination = navController.currentBackStackEntryAsState()?.value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(
                    item.icon,
                    contentDescription = item.label,
                    tint = if(currentDestination == item.route) primaryColor else primaryColor.copy(alpha = 0.6f)
                ) },
                label = { Text(
                    item.label,
                    color = if(currentDestination == item.route) primaryColor else primaryColor.copy(alpha = 0.8f)
                    ) },
                selected = currentDestination == item.route,
                onClick = {
                    if (currentDestination != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )
        }
    }
}


data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)
