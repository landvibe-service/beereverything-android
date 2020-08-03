package com.landvibe.beereverything.beerdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String
)