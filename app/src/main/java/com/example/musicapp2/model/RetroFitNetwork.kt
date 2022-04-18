package com.example.musicapp2

import com.example.musicapp2.model.MusicResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {
    @GET(END_POINT)
    fun findMusicByTitle(@Query(TERM) Term: String,
                         @Query(AMP_MEDIA) media: String,
                         @Query(AMP_ENTITY) entity: String,
                         @Query(AMP_LIMIT) limit:String,

    ): Call<MusicResponse>
    companion object
    {
        private const val BASE_URL = "https://itunes.apple.com/"
        private const val END_POINT = "search"
        private const val TERM = "term"
        private const val AMP_MEDIA = "amp;media"
        private const val AMP_ENTITY = "amp;entity"
        private const val AMP_LIMIT = "amp;limit"


        fun initRetrofit(): MusicApi
        {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MusicApi::class.java)
        }
    }
}