package com.example.catsnducks.ui.main

import androidx.lifecycle.*
import com.example.catsnducks.data.api.PictureService
import com.example.catsnducks.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getPic(pictureService: PictureService) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = pictureService.getRandomCatPic()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }
}