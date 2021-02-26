package com.example.weighttracker.weightTracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weighttracker.database.WeightDatabase
import com.example.weighttracker.database.WeightDatabaseDao

class WeightTrackerViewModelFactory(
    private val dataSource: WeightDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeightTrackerViewModel::class.java)) {
            return WeightTrackerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}