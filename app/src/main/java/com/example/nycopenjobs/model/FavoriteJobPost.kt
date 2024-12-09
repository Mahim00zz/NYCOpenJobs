package com.example.nycopenjobs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_job_posts")
data class FavoriteJobPost(
    @PrimaryKey val jobId: Int
)
