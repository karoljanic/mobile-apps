package com.example.photosgallery2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.photosgallery2.ImagesRepository
import com.example.photosgallery2.R
import com.example.photosgallery2.adapters.GalleryAdapter

class GalleryFragment : Fragment() {
    lateinit var galleryAdapter: GalleryAdapter
    lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        viewPager2 = view.findViewById(R.id.viewPager2)

        galleryAdapter = GalleryAdapter(ImagesRepository.getAllImages(), viewPager2)

        viewPager2.adapter = galleryAdapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        return view
    }
}