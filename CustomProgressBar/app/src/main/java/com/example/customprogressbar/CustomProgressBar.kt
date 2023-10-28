package com.example.customprogressbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class CustomProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var outerRadius: Float = 200.0F
        set(value) {
            if (value > 0) {
                field = value
                invalidate()
            }
        }

    var innerRadius: Float = 150.0F
        set(value) {
            if (value > 0) {
                field = value
                invalidate()
            }
        }

    var padding: Float = 20.0F
        set(value) {
            if (value > 0) {
                field = value
                invalidate()
            }
        }

    var progress: Float = 0.0F
        set(value) {
            if (value in 0.0F..1.0F) {
                field = value
                invalidate()
            }
        }

    var firstColor: Int = Color.BLUE
        set(value) {
            field = value
            invalidate()
        }

    var secondColor: Int = Color.GRAY
        set(value) {
            field = value
            invalidate()
        }

    private var gradientMatrix = Matrix()

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2f
        val centerY = height / 2f

        val strokeWidth = outerRadius - innerRadius
        val radius = outerRadius - strokeWidth / 2

        gradientMatrix.mapPoints(
            floatArrayOf(
                centerX - radius + padding,
                centerY - radius + padding,
                centerX + radius - padding,
                centerY + radius - padding
            )
        )

        val gradientShader = LinearGradient(
            centerX - radius + padding,
            centerY - radius + padding,
            centerX + radius - padding,
            centerY + radius - padding,
            firstColor,
            secondColor,
            Shader.TileMode.CLAMP
        )

        paint.strokeWidth = strokeWidth
        paint.shader = gradientShader

        canvas.drawArc(
            centerX - radius + padding,
            centerY - radius + padding,
            centerX + radius - padding,
            centerY + radius - padding,
            0.0F,
            360.0F * progress,
            false,
            paint
        )
    }
}