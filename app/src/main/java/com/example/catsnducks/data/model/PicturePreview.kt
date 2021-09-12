package com.example.catsnducks.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PicturePreview (
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null,
    @PrimaryKey (autoGenerate = true)
    var id : Int? = null
)