package com.example.photosgallery2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.photosgallery2.adapters.GalleryAdapter
import com.example.photosgallery2.adapters.MainViewPagerAdapter
import com.example.photosgallery2.fragments.GalleryFragment
import com.example.photosgallery2.fragments.PhotoFragment


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 0..5) {
            ImagesRepository.addWebImage(
                "https://picsum.photos/id/${10 + i}/200/300",
                "Title $i",
                "Description $i",
                0
            )
        }

        viewPager = findViewById(R.id.main_pager)
        viewPager.adapter = MainViewPagerAdapter(this)
        viewPager.currentItem = 1

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val fragment = supportFragmentManager.fragments[position]
                if (fragment is PhotoFragment) {
                    val lastFragment = supportFragmentManager.fragments[1] as GalleryFragment
                    val currentPosition: Int = lastFragment.viewPager2.currentItem

                    fragment.setCurrentImage(lastFragment.galleryAdapter.imageList[currentPosition])
                }
            }
        })
    }
}