package com.example.nycopenjobs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nycopenjobs.ui.screens.HomeScreen
import com.example.nycopenjobs.ui.screens.JobDetailsScreen
import com.example.nycopenjobs.ui.screens.HomeScreenViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController, homeScreenViewModel) }
        composable("job_details/{jobId}") { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")
            jobId?.let {
                val jobPost = homeScreenViewModel.getJobPostById(it)
                if (jobPost != null) {
                    JobDetailsScreen(
                        jobPost = jobPost,
                        navController = navController,
                        homeScreenViewModel = homeScreenViewModel,
                        isFavorite = homeScreenViewModel.getJobPostById(it) != null,
                        onFavoriteClick = {

                            homeScreenViewModel.toggleFavorite(jobPost)
                        }
                    )
                }
            }
        }
    }
}