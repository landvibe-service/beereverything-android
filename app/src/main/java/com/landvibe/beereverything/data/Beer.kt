package com.landvibe.beereverything.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "country") var country: String? = "",
    @ColumnInfo(name = "volume") var volume: Int? = 0,
    @ColumnInfo(name = "score") var score: Int? = 0
)