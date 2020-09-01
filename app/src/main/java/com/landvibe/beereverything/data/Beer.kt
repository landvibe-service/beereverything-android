package com.landvibe.beereverything.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "name_en") var name_en: String? = "",
    @ColumnInfo(name = "country") var country: String? = "",
    @ColumnInfo(name = "volume") var volume: Int? = 0,
    @ColumnInfo(name = "category") var category: String? = "",
    @ColumnInfo(name = "favorite") var favorite : Boolean? = false,
    @ColumnInfo(name = "image_url") var image_url : String = ""
    //@ColumnInfo(name = "score") var score: Int? = 0
)