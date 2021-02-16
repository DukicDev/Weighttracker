package com.example.weighttracker.weightHistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.weighttracker.database.WeightDatabaseDao
import com.example.weighttracker.formatEntries
import kotlinx.coroutines.*

class WeightHistoryViewModel(
    private val database: WeightDatabaseDao,
    application: Application
) : AndroidViewModel(application){

    private val allEntries = database.getAllEntries()
    val weightsString = Transformations.map(allEntries){
        formatEntries(it, application.resources)
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _eventOnClearButtonClick = MutableLiveData<Boolean>()
    val eventOnClearButtonClick: LiveData<Boolean>
    get() = _eventOnClearButtonClick

    fun onClear(){
        _eventOnClearButtonClick.value = true
    }

    fun clearAll(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        return withContext(Dispatchers.IO){
            database.clear()
        }
    }

    fun finishedClear(){
        _eventOnClearButtonClick.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}