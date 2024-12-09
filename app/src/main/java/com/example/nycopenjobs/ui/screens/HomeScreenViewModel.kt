package com.example.nycopenjobs.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.nycopenjobs.NYCOpenJobsApp
import com.example.nycopenjobs.data.AppRepository
import com.example.nycopenjobs.data.FavJobPostDao
import com.example.nycopenjobs.data.JobPostDao
import com.example.nycopenjobs.data.LocalDatabase
import com.example.nycopenjobs.model.FavoriteJobPost
import com.example.nycopenjobs.model.JobPost
import com.example.nycopenjobs.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeScreenUIState {
    data class Success(val data: List<JobPost>) : HomeScreenUIState
    object Error : HomeScreenUIState
    object Loading : HomeScreenUIState
    object Ready : HomeScreenUIState
}

class HomeScreenViewModel(
    private val appRepository: AppRepository,
    private val localDatabase: LocalDatabase,
    private val jobPostDao: JobPostDao,
    private val favJobPostDao: FavJobPostDao,
) : ViewModel() {

    var uiState: HomeScreenUIState by mutableStateOf(HomeScreenUIState.Ready)
        private set

    init {
        getJobPostings()
    }

    fun getJobPostings() {
        viewModelScope.launch {
            uiState = HomeScreenUIState.Loading
            uiState = try {
                val jobPosts = appRepository.getJobPostings().map { jobPost ->
                    jobPost.copy(isFavorite = favJobPostDao.isFavorite(jobPost.jobId))
                }
                HomeScreenUIState.Success(jobPosts)
            } catch (e: IOException) {
                e.message?.let { Log.e(TAG, it) }
                HomeScreenUIState.Error
            } catch (e: HttpException) {
                e.message?.let { Log.e(TAG, it) }
                HomeScreenUIState.Error
            }
        }
    }

    fun getScrollPosition(): Int {
        return appRepository.getScrollPosition()
    }

    fun setScrollPosition(position: Int) {
        appRepository.setScrollPosition(position)
    }

    fun toggleFavorite(jobPost: JobPost) {
        viewModelScope.launch {
            val newJobPost = jobPost.copy(isFavorite = !jobPost.isFavorite)
            if (newJobPost.isFavorite) {
                // favorite
                favJobPostDao.markAsFavorite(FavoriteJobPost(newJobPost.jobId))
            } else {
                // Unfav
                favJobPostDao.unmarkAsFavorite(FavoriteJobPost(newJobPost.jobId))
            }
            // refetch
            getJobPostings()
        }
    }

    fun getJobPostById(jobId: String): JobPost? {
        val jobIdInt = jobId.toIntOrNull()
        return if (jobIdInt != null) {
            (uiState as? HomeScreenUIState.Success)?.data?.find { it.jobId == jobIdInt }
        } else {
            null
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                Log.i(TAG, "view model factoryL getting get app container")
                val application = checkNotNull(extras[APPLICATION_KEY]) as NYCOpenJobsApp
                val appContainer = application.container
                return HomeScreenViewModel(
                    appContainer.appRepository,
                    appContainer.localDatabase,
                    appContainer.localDatabase.jobPostDao(),
                    appContainer.localDatabase.favJobPostDao()
                ) as T
            }
        }
    }
}

    