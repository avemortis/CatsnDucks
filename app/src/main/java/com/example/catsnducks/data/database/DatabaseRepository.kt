package com.example.catsnducks.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.catsnducks.data.model.Picture
import com.example.catsnducks.data.model.PicturePreview
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "cats_and_ducks"

class DatabaseRepository private constructor(context: Context){
    private val database : CatsAndDucksDatabase = Room.databaseBuilder(
        context.applicationContext,
        CatsAndDucksDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val pictureDAO = database.picDAO()
    private val executor = Executors.newSingleThreadExecutor()

    fun getPictures(): LiveData<List<Picture>> = pictureDAO.getPictures()

    fun getPreviewForPicture(id : Int) = pictureDAO.getPreviewForPicture(id)

    fun deletePicture(picture: Picture){
        executor.execute{
            pictureDAO.deletePicture(picture)
        }
    }

    fun addPicture(picture: Picture){
        executor.execute{
            pictureDAO.addPicture(picture)
        }
    }

    fun addPreview(preview: PicturePreview){
        executor.execute{
            pictureDAO.addPreview(preview)
        }
    }

    companion object {
        private var INSTANCE: DatabaseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DatabaseRepository(context)
            }
        }

        fun get(): DatabaseRepository {
            return INSTANCE ?:
            throw IllegalStateException("DatabaseRepository must be initialized")
        }
    }
}