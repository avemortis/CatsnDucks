package com.example.catsnducks.adapters

interface RecyclerViewItemStateChangeListener {
    fun onCreate(position: Int)
    fun onDestroy(position: Int)
}