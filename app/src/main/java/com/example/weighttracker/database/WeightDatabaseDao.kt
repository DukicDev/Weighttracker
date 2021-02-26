package com.example.weighttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeightDatabaseDao {
    @Insert
    fun insert(weightEntry: WeightEntry)

    @Update
    fun update(weightEntry: WeightEntry)

    @Query("SELECT * FROM daily_weight_table WHERE weightId = :key")
    fun get(key: Long): WeightEntry?

    @Query("DELETE FROM daily_weight_table")
    fun clear()

    @Query("SELECT * FROM daily_weight_table ORDER BY weightId DESC")
    fun getAllEntries(): LiveData<List<WeightEntry>>

    @Query("SELECT * FROM daily_weight_table ORDER BY weightId DESC LIMIT 1")
    fun getTodaysEntry(): WeightEntry?
}