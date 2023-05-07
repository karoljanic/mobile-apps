package com.example.photosgallery.ui

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.photosgallery.PhotoActivity
import com.example.photosgallery.model.ImageModel

@Composable
fun PhotosGalleryView(images: ArrayList<ImageModel>) {
    val sortedImages = remember { mutableStateListOf(*images.sortedBy { -it.rate }.toTypedArray()) }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(items = sortedImages, key = { it.id }) { image ->
            PhotoItem(image) {
                sortedImages.clear()
                sortedImages.addAll(images.sortedBy { -it.rate })
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoItem(imageModel: ImageModel, onActivityResult: () -> Unit) {
    val currentContext = LocalContext.current
    val intent = Intent(currentContext, PhotoActivity::class.java)
    intent.putExtra("image-id", imageModel.id.toString())

    val activityResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        onActivityResult()
    }


    Box(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(onTap = {
            activityResultLauncher.launch(intent)
        })
    }) {
        GlideImage(
            model = imageModel.url,
            contentDescription = imageModel.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}