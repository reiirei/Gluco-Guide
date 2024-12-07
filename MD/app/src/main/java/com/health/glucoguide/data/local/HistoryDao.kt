package com.health.glucoguide.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.health.glucoguide.models.HistoryEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getHistories(): LiveData<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistories(history: List<HistoryEntity>)

    @Query("DELETE FROM history")
    suspend fun deleteHistories()
}