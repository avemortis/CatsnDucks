package com.example.catsnducks.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catsnducks.data.database.DatabaseRepository
import com.example.catsnducks.data.model.Picture

class GalleryViewModel : ViewModel() {
    var galleryPictures = DatabaseRepository.get().getPictures()
}