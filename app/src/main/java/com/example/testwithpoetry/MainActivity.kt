package com.example.testwithpoetry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.testwithpoetry.ui.theme.TestWithPoetryTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.testwithpoetry.ui.navigation.NavigationGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestWithPoetryTheme {
                val navController = rememberNavController()
                NavigationGraph(navController = navController, mainViewModel = hiltViewModel())
                //MainScreen(viewModel = hiltViewModel())
            }
        }
    }
}

