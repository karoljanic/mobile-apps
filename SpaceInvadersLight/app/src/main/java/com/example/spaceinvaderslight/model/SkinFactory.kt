package com.example.spaceinvaderslight.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.spaceinvaderslight.R

object SkinFactory {
    fun getPlayer1Skin(context: Context, width: Int, height: Int): Bitmap {
        val tmp = BitmapFactory.decodeResource(context.resources, R.drawable.player1)

        return Bitmap.createScaledBitmap(tmp, width, height, false)
    }

    fun getPlayer2Skin(context: Context, width: Int, height: Int): Bitmap {
        val tmp = BitmapFactory.decodeResource(context.resources, R.drawable.player2)

        return Bitmap.createScaledBitmap(tmp, width, height, false)
    }

    fun getInvader1Skin(context: Context, width: Int, height: Int): Bitmap {
        val tmp = BitmapFactory.decodeResource(context.resources, R.drawable.invader1)

        return Bitmap.createScaledBitmap(tmp, width, height, false)
    }

    fun getInvader2Skin(context: Context, width: Int, height: Int): Bitmap {
        val tmp = BitmapFactory.decodeResource(context.resources, R.drawable.invader2)

        return Bitmap.createScaledBitmap(tmp, width, height, false)
    }
}