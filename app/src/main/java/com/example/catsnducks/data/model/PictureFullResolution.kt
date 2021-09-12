package com.example.catsnducks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PictureFullResolution (
    var mainId : Int = 0,
    @PrimaryKey (autoGenerate = true)
    var id : Int? = null
)