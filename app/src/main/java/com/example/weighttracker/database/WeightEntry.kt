package com.example.weighttracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weight_table")
data class WeightEntry(
    @PrimaryKey(autoGenerate = true)
    var weightId: Long = 0L,

    @ColumnInfo
    val currentTime: Long = System.currentTimeMillis(),

    @ColumnInfo
    val weight: Double = 0.0
)