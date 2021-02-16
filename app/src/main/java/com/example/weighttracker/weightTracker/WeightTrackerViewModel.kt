package com.example.weighttracker.weightTracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weighttracker.database.WeightDatabaseDao
import com.example.weighttracker.database.WeightEntry
import kotlinx.coroutines.*

class WeightTrackerViewModel(private val database: WeightDatabaseDao,
application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val allEntries = database.getAllEntries()

    private val _eventOnAddButtonClick = MutableLiveData<Boolean>()
    val eventOnAddButtonClick: LiveData<Boolean>
    get() = _eventOnAddButtonClick

    fun calcAvg(entries: List<WeightEntry>): Double{
        val counter: Int = 7.coerceAtMost(entries.size)
        var result: Double = 0.0
        for (elem in entries.subList(0, counter)){
            result += elem.weight
        }
        result /= counter
        return result
    }

    fun onAdd(){
        _eventOnAddButtonClick.value = true
    }

    fun addWeight(weightValue: Double){
        uiScope.launch {
            val newWeight = WeightEntry(weight = weightValue)
            insert(newWeight)
        }
    }

    private suspend fun insert(weightEntry: WeightEntry){
        return withContext(Dispatchers.IO){
            database.insert(weightEntry)
        }
    }

    fun finishedOnAdd(){
        _eventOnAddButtonClick.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}