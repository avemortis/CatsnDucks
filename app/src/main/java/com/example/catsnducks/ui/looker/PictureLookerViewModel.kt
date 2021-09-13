package com.example.catsnducks.ui.looker

import androidx.lifecycle.ViewModel

class PictureLookerViewModel : ViewModel() {
    var url : String = String()
    var position : Int? = null
    var touched = false
    var liked : Boolean? = false
}