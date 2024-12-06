package com.health.glucoguide.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "diagnosa")
    val diagnosa: String? = null,

    @ColumnInfo(name = "tanggal_cek")
    val tanggalCek: String? = null,

    @ColumnInfo(name = "keluhan")
    val keluhan: String? = null
)