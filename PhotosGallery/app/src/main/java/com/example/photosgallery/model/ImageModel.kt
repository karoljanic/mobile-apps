package com.example.photosgallery.model

data class ImageModel(
    val id: Int, val url: String, val title: String, val description: String, var rate: Int
)