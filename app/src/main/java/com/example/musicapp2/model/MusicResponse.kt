package com.example.musicapp2.model

data class MusicResponse(
    val results: List<MusicItem>
)
data class MusicItem(
    val artistName: String,
    val trackName: String,
    val previewUrl: String,
    val trackPrice: Double,
    val artworkUrl100: String,
    val currency: String
)

