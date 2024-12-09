package com.example.nycopenjobs.api

import android.util.Log
import com.example.nycopenjobs.model.JobPost
import com.example.nycopenjobs.util.TAG
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NYCOpenDataApi {

    @GET("kpav-sd4t.json?posting_type=External&\$order=posting_updated%20DESC&")

    suspend fun getJobPostings(
        @Query("\$offset") offset: Int,
        @Query("\$limit") limit: Int =50
    ): List<JobPost>



}

class AppRemoteApis {
    private val baseUrl = "https://data.cityofnewyork.us/resource/"

    private val contentType = MediaType.get("application/json; charset=utf-8")

    private val json = Json { ignoreUnknownKeys = true }

    fun getNYCOpenDataApi(): NYCOpenDataApi {
        Log.i(TAG, "retrofit create API call")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(NYCOpenDataApi::class.java)
    }


}

