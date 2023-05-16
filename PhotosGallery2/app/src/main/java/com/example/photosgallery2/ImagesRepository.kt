package com.example.photosgallery2

import android.util.Log
import com.example.photosgallery2.model.ImageModel
import com.example.photosgallery2.model.ImageType

object ImagesRepository {
    private val imagesList: ArrayList<ImageModel> = ArrayList()
    private var nextImageId: Int = 0

    fun addWebImage(url: String, title: String, description: String, rating: Int) {
        imagesList.add(ImageModel(nextImageId, url, title, description, rating, ImageType.FROM_WEB))
        nextImageId++
    }

    fun addStorageImage(url: String, title: String, description: String, rating: Int) {
        imagesList.add(ImageModel(nextImageId, url, title, description, rating, ImageType.IN_STORAGE))
        nextImageId++
    }

    fun updateImageRate(id: Int, newRate: Int) {
        val chosenImages = imagesList.filter { image -> image.id == id }
        for(image in chosenImages)
            image.rate = newRate
    }

    fun getAllImages(): ArrayList<ImageModel> {
        return imagesList
    }

    fun getImage(id: Int): ImageModel? {
        val chosenImages = imagesList.filter { image -> image.id == id }
        return if(chosenImages.isNotEmpty())
            chosenImages[0]
        else
            null
    }

}
