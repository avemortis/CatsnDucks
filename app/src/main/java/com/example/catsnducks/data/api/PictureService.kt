package com.example.catsnducks.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class PictureService @Inject constructor() {
    private val picAPI : PicAPI
    init {
        val catRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://example/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        picAPI = catRetrofit.create(PicAPI::class.java)
    }

    suspend fun getRandomCatPic() = picAPI.getRandomCatPic("https://thatcopy.pw/catapi/rest/")

    suspend fun getRandomDuckPic() = picAPI.getRandomDuckPic("https://random-d.uk/api/v2/random")
}