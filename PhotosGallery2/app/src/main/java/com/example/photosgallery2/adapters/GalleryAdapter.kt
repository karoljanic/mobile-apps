package com.example.photosgallery2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.photosgallery2.R
import com.example.photosgallery2.model.ImageModel
import com.example.photosgallery2.model.ImageType
import com.squareup.picasso.Picasso
import java.io.File

class GalleryAdapter(val imageList: ArrayList<ImageModel>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageList[position]

        if(image.type == ImageType.FROM_WEB) {
            Picasso.get()
                .load(image.url)
                .into(holder.imageView)
        }
        else {
            Picasso.get()
                .load(File(image.url))
                .into(holder.imageView)
        }

        if (position == imageList.size-1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}