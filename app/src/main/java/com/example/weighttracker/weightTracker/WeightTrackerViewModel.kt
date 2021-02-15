package com.example.weighttracker.weightTracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.weighttracker.database.WeightDatabase
import com.example.weighttracker.database.WeightDatabaseDao

class WeightTrackerViewModel(val database: WeightDatabaseDao,
application: Application): AndroidViewModel(application) {

}