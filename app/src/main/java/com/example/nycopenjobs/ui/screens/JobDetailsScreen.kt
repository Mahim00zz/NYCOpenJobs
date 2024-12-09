package com.example.nycopenjobs.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nycopenjobs.model.JobPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
    jobPost: JobPost,
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    isFavorite: Boolean,
    onFavoriteClick: (JobPost) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Navigation Bar
        TopAppBar(
            title = {
                Text(
                    text = jobPost.businessTitle,
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            },
            actions = {
                // Favorite Button
                IconButton(
                    onClick = {
                        onFavoriteClick(jobPost)
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) Color.White else Color.Red
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFADD8E6),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )

        //  Job Details
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            item {
                // Job Title
                Text(
                    text = jobPost.businessTitle,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                // Agency Section
                Text(
                    text = "Agency",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = jobPost.agency,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Job Category
                Text(
                    text = "Job Category",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = jobPost.jobCategory,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Salary Section
                Text(
                    text = "Salary",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${jobPost.salaryRangeFrom} - ${jobPost.salaryRangeTo} ${jobPost.salaryFrequency}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Full/Part-Time Section
                Text(
                    text = "Full-Time / Part-Time",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = if (jobPost.fullOrPartTime == 'F') "Full-Time" else "Part-Time",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Work Location
                Text(
                    text = "Work Location",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = jobPost.workLocation,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Job Description
                Text(
                    text = "Job Description",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = jobPost.jobDescription,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Minimum Requirements
                if (jobPost.minRequirement.isNotEmpty()) {
                    Text(
                        text = "Minimum Requirements",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = jobPost.minRequirement,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }

                // Preferred Skills
                if (jobPost.preferredSkills.isNotEmpty()) {
                    Text(
                        text = "Preferred Skills",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = jobPost.preferredSkills,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }

                // Additional Information
                if (jobPost.additionalInfo.isNotEmpty()) {
                    Text(
                        text = "Additional Information",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = jobPost.additionalInfo,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }

                // Apply Information
                if (jobPost.toApply.isNotEmpty()) {
                    Text(
                        text = "How to Apply",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = jobPost.toApply,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }

                // Posting Dates
                Text(
                    text = "Posting Date",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = jobPost.postingDate,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Posting Expiry Date (Optional)
                if (jobPost.postUntil.isNotEmpty()) {
                    Text(
                        text = "Post Until",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = jobPost.postUntil,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }

                // Posting Last Updated
                Text(
                    text = "Last Updated",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = jobPost.postingLastUpdated,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )
            }
        }
    }
}
