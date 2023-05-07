package com.example.photosgallery.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.photosgallery.ImagesRepository
import com.example.photosgallery.model.ImageModel


@Composable
fun PhotoView(imageModel: ImageModel) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            PhotoViewHorizontal(imageModel)
        }
        else -> {
            PhotoViewPortrait(imageModel)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PhotoViewPortrait(imageModel: ImageModel) {
    val screenDimension = LocalConfiguration.current.screenHeightDp

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = imageModel.title, fontSize = 25.sp)

        Text(text = imageModel.description, fontSize = 20.sp)

        GlideImage(
            model = imageModel.url,
            contentDescription = imageModel.description,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.height((screenDimension * 0.6).dp)
        )

        Row(modifier = Modifier.height((screenDimension * 0.2).dp)) {
            RatingBar(rating = imageModel.rate) { newRate: Int ->
                ImagesRepository.updateImageRate(imageModel.id, newRate)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PhotoViewHorizontal(imageModel: ImageModel) {
    val screenWidthDimension = LocalConfiguration.current.screenWidthDp
    val screenHeightDimension = LocalConfiguration.current.screenHeightDp

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = imageModel.url,
            contentDescription = imageModel.description,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height((screenHeightDimension * 0.9).dp)
                .width((screenWidthDimension * 0.5).dp)
        )
        
        Column(verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = imageModel.title, fontSize = 25.sp)

            Text(text = imageModel.description, fontSize = 20.sp)

            Row(modifier = Modifier.height((screenHeightDimension * 0.2).dp)) {
                RatingBar(rating = imageModel.rate) { newRate: Int ->
                    ImagesRepository.updateImageRate(imageModel.id, newRate)
                }
            }
        }
    }
}
