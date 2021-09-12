package com.example.catsnducks.utils

import androidx.lifecycle.LifecycleOwner
import com.example.catsnducks.data.model.Picture
import com.example.catsnducks.data.database.DatabaseRepository

class QueueDownloader(val lifecycleOwner: LifecycleOwner, val pictures : List<Picture>, val loadListener: LoadListener) {
    private var count = 0

    fun start() {
        if (pictures.isNotEmpty())
            get()
    }

    private fun get(){
        val repository = DatabaseRepository.get()
        val picture = pictures[count]
        val preview = repository.getPreviewForPicture(picture.id!!)
        preview.observe(lifecycleOwner, {
            pictures[count].image = it.image
            preview.removeObservers(lifecycleOwner)
            push()
        })
    }

    private fun push(){
        loadListener.onSingleLoad()
        count++

        if (count < pictures.size)
            get()
        else
            loadListener.onFullLoad()
    }
}