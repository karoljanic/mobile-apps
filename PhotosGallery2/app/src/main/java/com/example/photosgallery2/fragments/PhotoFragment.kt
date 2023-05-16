package com.example.photosgallery2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.photosgallery2.ImagesRepository
import com.example.photosgallery2.R
import com.example.photosgallery2.model.ImageModel
import com.example.photosgallery2.model.ImageType
import com.squareup.picasso.Picasso
import java.io.File

class PhotoFragment: Fragment() {
    private lateinit var imageTitle: TextView
    private lateinit var imageDescription: TextView
    private lateinit var imageRating: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        imageTitle = view.findViewById(R.id.imageTitle)
        imageDescription = view.findViewById(R.id.imageDescription)
        imageRating = view.findViewById(R.id.imageRating)

        imageRating.stepSize = 1.0f

        return view
    }

    fun setCurrentImage(imageModel: ImageModel) {
        setImage(requireView(), imageModel)
    }

    private fun setImage(view: View, imageModel: ImageModel) {
        imageTitle.text = imageModel.title
        imageDescription.text = imageModel.description
        imageRating.setOnRatingBarChangeListener { _, newRating, fromUser ->
            if(fromUser)
                ImagesRepository.updateImageRate(imageModel.id, newRating.toInt())
        }
        imageRating.rating = imageModel.rate.toFloat()

        if(imageModel.type == ImageType.FROM_WEB) {
            Picasso.get()
                .load(imageModel.url)
                .into(view.findViewById<ImageView>(R.id.imageView))
        }
        else {
            Picasso.get()
                .load(File(imageModel.url))
                .into(view.findViewById<ImageView>(R.id.imageView))
        }

        Picasso.get()

    }
}