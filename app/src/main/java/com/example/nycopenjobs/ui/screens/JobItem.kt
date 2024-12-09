package com.example.nycopenjobs.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nycopenjobs.model.JobPost

@Composable
fun JobItem(jobPost: JobPost, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate("job_details/${jobPost.jobId}")
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (jobPost.careerLevel.isNotEmpty()) {
                    Text(
                        text = "Experience Required: ${jobPost.careerLevel}",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Text(
                    text = jobPost.agency,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = jobPost.businessTitle,
                    style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }


            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View Details",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
