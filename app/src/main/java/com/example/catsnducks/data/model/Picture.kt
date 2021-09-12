package com.example.catsnducks.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
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
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null
)