package com.example.catsnducks.data.components

import android.os.Bundle
import com.example.catsnducks.ui.looker.PictureLookerDialogFragment
import javax.inject.Inject

class DialogFragmentBundleFactory @Inject constructor() {
    fun getPictureLookerBundle(position: Int?) : Bundle{
        val bundle : Bundle = Bundle()
        if (position!=null){
            val liked = true
            bundle.putString(PictureLookerDialogFragment.URL_TAG, String())
            bundle.putInt(PictureLookerDialogFragment.POSITION_TAG, position)
            bundle.putBoolean(PictureLookerDialogFragment.LIKE_TAG, liked)
        }
        return bundle
    }
}