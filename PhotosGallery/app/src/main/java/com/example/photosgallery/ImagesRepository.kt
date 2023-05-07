package com.example.photosgallery

import com.example.photosgallery.model.ImageModel

object ImagesRepository {
    private val imagesList: ArrayList<ImageModel> = ArrayList()
    private var nextImageId: Int = 0

    fun addImage(url: String, title: String, description: String, rating: Int) {
        imagesList.add(ImageModel(nextImageId, url, title, description, rating))
        nextImageId++
    }

    fun updateImageRate(id: Int, newRate: Int) {
        val chosenImages = imagesList.filter { image -> image.id == id }
        if(chosenImages.size == 1)
            chosenImages[0].rate = newRate
    }

    fun getAllImages(): ArrayList<ImageModel> {
        return imagesList
    }

    fun getImage(id: Int): ImageModel? {
        val chosenImages = imagesList.filter { image -> image.id == id }
        return if(chosenImages.size == 1)
            chosenImages[0]
        else
            null
    }
}
