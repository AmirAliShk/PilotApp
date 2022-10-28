package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(vararg locations: LocationsTable)

    @Query("select * from locations")
    fun getLocations(): List<LocationsTable>

}