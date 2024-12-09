package com.example.nycopenjobs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.nycopenjobs.ui.navigation.AppNavigation
import com.example.nycopenjobs.ui.screens.HomeScreenViewModel
import com.example.nycopenjobs.ui.theme.NYCOpenjobsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCOpenjobsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val navController = rememberNavController()


                    val viewModel: HomeScreenViewModel by viewModels { HomeScreenViewModel.Factory }

                    // AppNavigation
                    AppNavigation(
                        navController = navController,
                        homeScreenViewModel = viewModel
                    )
                }
            }
        }
    }
}

