package com.example.catsnducks.ui.morpheus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.catsnducks.data.api.PictureService
import com.example.catsnducks.utils.PictureTypes
import com.example.catsnducks.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MorpheusViewModel : ViewModel() {
    fun getPic(pictureService: PictureService, pictureTypes: PictureTypes) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            when (pictureTypes) {
                PictureTypes.CATS -> {
                    emit(Resource.success(data = pictureService.getRandomCatPic()))
                }
                PictureTypes.DUCKS -> {
                    emit(Resource.success(data = pictureService.getRandomDuckPic()))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }
}