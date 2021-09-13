package com.example.catsnducks.data.components

import com.example.catsnducks.data.api.PictureService
import com.example.catsnducks.data.model.Picture
import dagger.Component

@Component
interface PictureServiceComponent {
    fun getPictureService(): PictureService

    fun getDialogFragmentBundleFactory() : DialogFragmentBundleFactory
}