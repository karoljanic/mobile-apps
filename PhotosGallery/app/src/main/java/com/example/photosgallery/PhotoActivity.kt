package com.example.photosgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.photosgallery.model.ImageModel
import com.example.photosgallery.ui.PhotoView
import com.example.photosgallery.ui.theme.PhotosGalleryTheme

class PhotoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val photoId: Int = intent.getStringExtra("image-id")!!.toInt()
        val photo: ImageModel = ImagesRepository.getImage(photoId)!!

        setContent {
            PhotosGalleryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    PhotoView(photo)
                }
            }
        }
    }
}