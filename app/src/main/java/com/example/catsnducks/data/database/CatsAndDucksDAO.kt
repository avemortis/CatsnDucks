package com.example.catsnducks.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.catsnducks.data.model.Picture
import com.example.catsnducks.data.model.PicturePreview

@Dao
interface CatsAndDucksDAO {
    @Query("SELECT * FROM Picture")
    fun getPictures(): LiveData<List<Picture>>

    @Query("SELECT * FROM PicturePreview WHERE id LIKE:id")
    fun getPreviewForPicture(id: Int): LiveData<PicturePreview>


    @Insert
    fun addPicture(picture: Picture)

    @Insert
    fun addPreview(preview: PicturePreview)

    @Delete
    fun deletePicture(picture: Picture)
}