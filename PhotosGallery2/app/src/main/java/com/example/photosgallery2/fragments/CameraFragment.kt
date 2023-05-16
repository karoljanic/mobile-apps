package com.example.photosgallery2.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.photosgallery2.ImagesRepository
import com.example.photosgallery2.R
import java.io.File
import java.util.concurrent.Executors
import kotlin.math.abs


class CameraFragment : Fragment() {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler(requireActivity().mainLooper)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onResume() {
        super.onResume()
        requestPermission()
    }

    private fun requestPermission() {
        requestCameraPermissionIfMissing { granted ->
            if (granted) startCamera()
            else handler.post {
                Toast.makeText(
                    context, "Allow Camera Permission!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun requestCameraPermissionIfMissing(onResult: ((Boolean) -> Unit)) {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) onResult(true)
        else {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                onResult(it)
            }.launch(Manifest.permission.CAMERA)
        }
    }

    fun startCamera() {
        val previewView = requireView().findViewById<PreviewView>(R.id.previewView)
        val capture = requireView().findViewById<ImageButton>(R.id.capture)

        val aspectRatio = aspectRatio(previewView.width, previewView.height)
        val listenableFuture = ProcessCameraProvider.getInstance(requireContext())

        listenableFuture.addListener({
            try {
                val cameraProvider = listenableFuture.get() as ProcessCameraProvider
                val preview = Preview.Builder().setTargetAspectRatio(aspectRatio).build()

                val imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                val cameraSelector =
                    CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                cameraProvider.unbindAll()
                val camera =
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

                capture.setOnClickListener {
                    takePicture(imageCapture)
                }

                preview.setSurfaceProvider(previewView.surfaceProvider)

            } catch (exception: Exception) {
                handler.post {
                    Toast.makeText(
                        context, "Cannot Run Camera! Try Again!", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePicture(imageCapture: ImageCapture) {
        val imageName = "${System.currentTimeMillis()}.jpg"
        val file = File(requireActivity().externalMediaDirs.first(), imageName)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(outputFileOptions,
            Executors.newCachedThreadPool(),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    handler.post {
                        Toast.makeText(
                            context, "Photo Was Taken! Check Gallery!", Toast.LENGTH_LONG
                        ).show()
                    }

                    ImagesRepository.addStorageImage(
                        file.canonicalPath,
                        imageName,
                        "The photo was taken with a camera!",
                        1
                    )

                    startCamera()
                }

                override fun onError(exception: ImageCaptureException) {
                    handler.post {
                        Toast.makeText(
                            context, "Photo Was Not Taken! Try Again!", Toast.LENGTH_LONG
                        ).show()
                    }
                    startCamera()
                }
            })
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = width.coerceAtLeast(height).toDouble() / width.coerceAtMost(height)
        return if (abs(previewRatio - 4.0 / 3.0) <= abs(previewRatio - 16.0 / 9.0)) {
            AspectRatio.RATIO_4_3
        } else AspectRatio.RATIO_16_9
    }

}