package com.example.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationsTable(
    @PrimaryKey(autoGenerate = true) var recordId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "east") val east: Double,
    @ColumnInfo(name = "north") val north: Double,
    @ColumnInfo(name = "elevation") val elevation: Double,
)