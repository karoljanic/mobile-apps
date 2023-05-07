package com.example.photosgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.photosgallery.ui.PhotosGalleryView
import com.example.photosgallery.ui.theme.PhotosGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (i in 0..50) {
            ImagesRepository.addImage(
                "https://picsum.photos/id/${10 + i}/200/300",
                "Title $i",
                "Description $i",
                0
            )
        }

        setContent {
            PhotosGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    PhotosGalleryView(ImagesRepository.getAllImages())
                }
            }
        }
    }
}
