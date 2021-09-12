package com.example.catsnducks

import android.app.Application
import android.content.Context
import com.example.catsnducks.data.components.DaggerPictureServiceComponent
import com.example.catsnducks.data.components.PictureServiceComponent
import com.example.catsnducks.data.database.DatabaseRepository

class CatsAndDugsApplication : Application() {
    lateinit var pictureServiveComponent: PictureServiceComponent

    override fun onCreate() {
        super.onCreate()
        pictureServiveComponent = DaggerPictureServiceComponent.create()
        DatabaseRepository.initialize(this)
    }
}

val Context.appComponent: PictureServiceComponent
get() = when (this) {
    is CatsAndDugsApplication -> pictureServiveComponent
    else -> this.applicationContext.appComponent
}