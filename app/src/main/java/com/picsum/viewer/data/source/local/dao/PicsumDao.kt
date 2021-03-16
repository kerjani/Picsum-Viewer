package com.picsum.viewer.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picsum.viewer.data.models.Item

@Dao
interface PicsumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weatherData: List<Item>)

    @Query("SELECT * FROM items")
    suspend fun getItems(): List<Item>

    @Query("DELETE FROM items")
    suspend fun deleteAll()
}