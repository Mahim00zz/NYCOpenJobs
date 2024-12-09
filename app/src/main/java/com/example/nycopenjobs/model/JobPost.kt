package com.example.nycopenjobs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class JobPost(
    @SerialName("job_id") @PrimaryKey val jobId: Int,
    val agency: String,
    @SerialName("number_of_positions") val numOfOpenPositions: String,
    @SerialName("business_title") val businessTitle: String,
    @SerialName("civil_service_title") val civilServiceTitle: String,
    @SerialName("job_category") val jobCategory: String,
    @SerialName("full_time_part_time_indicator") val fullOrPartTime: Char,
    @SerialName("career_level") val careerLevel: String,
    @SerialName("salary_range_from") val salaryRangeFrom: Double,
    @SerialName("salary_range_to") val salaryRangeTo: Double,
    @SerialName("salary_frequency") val salaryFrequency: String,
    @SerialName("work_location") val agencyLocation: String,
    @SerialName("division_work_unit") val divisionWorkUnit: String,
    @SerialName("job_description") val jobDescription: String,
    @SerialName("minimum_qual_requirements") val minRequirement: String = "",
    @SerialName("preferred_skills") val preferredSkills: String = "",
    @SerialName("additional_information") val additionalInfo: String = "",
    @SerialName("to_apply") val toApply: String = "",
    @SerialName("work_location_1") val workLocation: String = "",
    @SerialName("posting_date") val postingDate: String,
    @SerialName("post_until") val postUntil: String = "",
    @SerialName("posting_updated") val postingLastUpdated: String,

    @ColumnInfo(defaultValue = "0")
    @SerialName("is_favorite") val isFavorite: Boolean = false
)