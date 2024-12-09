package com.example.nycopenjobs.data

import android.content.SharedPreferences
import android.util.Log
import com.example.nycopenjobs.api.NYCOpenDataApi
import com.example.nycopenjobs.model.JobPost
import com.example.nycopenjobs.util.TAG
import kotlinx.coroutines.flow.first

interface AppRepository {

    fun getScrollPosition(): Int

    fun setScrollPosition(position: Int)

    suspend fun getJobPostings(): List<JobPost>

    suspend fun getJobPost(jobId: Int) : JobPost
}

class AppRepositoryImpl(
    private val nycOpenDataApi: NYCOpenDataApi,
    private val sharedPreferences: SharedPreferences,
    private val dao: JobPostDao,
) : AppRepository {

    private val scrollPositionKey = "Scroll_position"
    private var offsetkey = "offset"


    private var offset = sharedPreferences.getInt(offsetkey, 0)

    private var totalJobs = 0

    private fun updateOffset(){ offset += (totalJobs - offset)
        Log.i(TAG, "offset: $offset")
        sharedPreferences.edit().putInt(offsetkey, offset).apply()
    }

    private fun updateTotalJobs(newTotal: Int){
        totalJobs = newTotal
        Log.i(TAG, "total jobs: $totalJobs")
    }


    override suspend fun getJobPostings(): List<JobPost> {
        Log.i(TAG, "getting job postings")

        updateOffset()

        val localData = dao.getAll().first()
        updateTotalJobs(localData.size)
        if (offset == totalJobs) {
            Log.i(TAG, "getting job posting via API")

            val jobs = nycOpenDataApi.getJobPostings(offset)

            Log.i(TAG, "API returned ${jobs.size} jobs. Updating local database")
            dao.upsert(jobs)

            val updatedJobs = dao.getAll().first()

            updateTotalJobs(updatedJobs.size)

            Log.i(TAG, "return updated jobs from API")
            return updatedJobs
        }
        Log.i(TAG, "return local data")
        return localData
    }



    override suspend fun getJobPost(jobId: Int) : JobPost {
        Log.i(TAG, "getting job post $jobId")
        return dao.get(jobId)

    }
    override fun getScrollPosition(): Int {
        val scrollPosition = sharedPreferences.getInt(scrollPositionKey, 0)
        Log.i(TAG, "getting scroll position: $scrollPosition")

        return scrollPosition
    }

    override fun setScrollPosition(position: Int) {
        Log.i(TAG, "setting scroll position: $position")
        sharedPreferences.edit().putInt(scrollPositionKey, position).apply()
    }

}