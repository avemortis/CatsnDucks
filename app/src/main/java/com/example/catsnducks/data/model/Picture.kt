package com.example.catsnducks.data.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.inject.Inject

@Entity
data class Picture constructor (
    @SerializedName("url")
    var url : String = String(),
    @SerializedName("")
    @PrimaryKey (autoGenerate = true)
    var id : Int? = null,
    @Ignore
    var image: ByteArray? = null,
    @Ignore
    var bitmap: Bitmap? = null
)