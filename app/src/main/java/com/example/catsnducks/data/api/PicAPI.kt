package com.example.catsnducks.data.api

import com.example.catsnducks.data.model.Picture
import retrofit2.http.GET
import retrofit2.http.Url

interface PicAPI {
    @GET()
    suspend fun getRandomCatPic(@Url url: String) : Picture

    @GET()
    suspend fun getRandomDuckPic(@Url url : String) : Picture
}