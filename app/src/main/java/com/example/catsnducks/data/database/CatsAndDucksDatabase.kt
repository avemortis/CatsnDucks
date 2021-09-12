package com.example.catsnducks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catsnducks.data.database.TypeConverter
import androidx.room.TypeConverters
import com.example.catsnducks.data.model.Picture
import com.example.catsnducks.data.model.PictureFullResolution
import com.example.catsnducks.data.model.PicturePreview

@Database(entities = [Picture::class, PicturePreview::class, PictureFullResolution::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class CatsAndDucksDatabase : RoomDatabase() {
    abstract fun picDAO(): CatsAndDucksDAO
}