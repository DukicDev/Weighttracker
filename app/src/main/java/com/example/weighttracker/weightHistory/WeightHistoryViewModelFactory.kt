package com.example.weighttracker.weightHistory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weighttracker.database.WeightDatabaseDao
import com.example.weighttracker.weightTracker.WeightTrackerViewModel

class WeightHistoryViewModelFactory(
    private val dataSource: WeightDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeightHistoryViewModel::class.java)) {
            return WeightHistoryViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}