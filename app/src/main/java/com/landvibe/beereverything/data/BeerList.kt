package com.landvibe.beereverything.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name") var name: String
    //@ColumnInfo(name = "price") var price : Int
)