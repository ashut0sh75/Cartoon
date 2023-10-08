package com.example.cartoon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SwitchCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.cartoon.ml.WhiteboxCartoonGanInt8
import com.shashank.sony.fancytoastlib.FancyToast
import org.tensorflow.lite.support.image.TensorImage
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class MainActivity : ComponentActivity() {

    companion object {
        // Request code for image selection
        private const val REQUEST_CODE_IMAGE = 1

        // Directory name for saving cartoon images
        private const val DIRECTORY_NAME = "CartoonImages"
    }


    private lateinit var imageView: ImageView       //Button for ImageView
    private lateinit var convertButton: Button        // Button for Convert Image to cartoon in image view
    private lateinit var saveButton: Button          // Button to save the converted image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize image view, convert button, and save button,Switch button
        imageView = findViewById(R.id.imageView)
        convertButton = findViewById(R.id.Convert)
        saveButton = findViewById(R.id.Save)





        // Set click listener for selecting an image
        findViewById<Button>(R.id.selectImagebtn).setOnClickListener {
            fetchImageFromStorage()
        }

        // Set click listener for converting the image to a cartoon
        convertButton.setOnClickListener {
            convertImageToCartoon()
        }

        // Set click listener for saving the cartoonized image
        saveButton.setOnClickListener {
            saveCartoonedImage()
        }
    }


    private fun fetchImageFromStorage() {
        // Create an intent to pick an image from external storage
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        // Start the activity to select an image and provide a request code
        startActivityForResult(intent, REQUEST_CODE_IMAGE)
    }

    private fun convertImageToCartoon() {
        // Get the drawable from the ImageView
        val drawable = imageView.drawable

        // Check if no image is selected
        if (drawable == null) {
            FancyToast.makeText(
                this@MainActivity,
                "No image selected",FancyToast.LENGTH_LONG,
                FancyToast.WARNING,true).show()
            return
        }

        // Load the image drawable into a TensorImage for inference
        val sourceImage = drawable.toTensorImage()

        // Perform inference to obtain the cartoonized image
        val cartoonizedImage = inferenceWithInt8Model(sourceImage)

        // Set the cartoonized image in the ImageView using Glide
        Glide.with(this)
            .load(cartoonizedImage.bitmap)
            .into(imageView)
    }


    private fun saveCartoonedImage() {
        // Get the drawable from the ImageView
        val drawable = imageView.drawable

        // Check if no image is selected
        if (drawable == null) {
            FancyToast.makeText(
                this@MainActivity,
                "No image selected",FancyToast.LENGTH_LONG,
                FancyToast.WARNING,true).show()
            return
        }

        // Convert the drawable to a TensorImage for saving
        val sourceImage = drawable.toTensorImage()

        // Check if external storage is available for read and write
        if (isExternalStorageWritable()) {
            // Define the directory to save the image
            val directory = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                DIRECTORY_NAME
            )

            // Create the directory if it doesn't exist
            if (!directory.exists()) {
                directory.mkdirs()
            }

            // Generate a unique filename for the saved image
            val fileName = "cartoon_${System.currentTimeMillis()}.jpg"
            val file = File(directory, fileName)

            // Save the image to the file using Glide
            Glide.with(this)
                .asBitmap()
                .load(sourceImage.bitmap)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        var outputStream: OutputStream? = null
                        try {
                            // Save the bitmap to the file
                            outputStream = FileOutputStream(file)
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                            outputStream.flush()

                            // Scan the saved image file to make it visible in the gallery
                            MediaScannerConnection.scanFile(
                                this@MainActivity,
                                arrayOf(file.toString()),
                                null,
                                null
                            )

                            FancyToast.makeText(
                                this@MainActivity,
                                "Image Saved",FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS,true).show()

                        } catch (e: Exception) {
                            e.printStackTrace()
                            FancyToast.makeText(this@MainActivity,"Failed to save image!",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show()

                        } finally {
                            outputStream?.close()
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Not used
                    }
                })
        } else {
            FancyToast.makeText(
                this,
                "External storage is not available",
                FancyToast.LENGTH_LONG,
                FancyToast.WARNING,
                true
            ).show()
        }
    }


    private fun isExternalStorageWritable(): Boolean {
        // Get the current state of the external storage
        val state = Environment.getExternalStorageState()

        // Check if the external storage is mounted and writable
        return Environment.MEDIA_MOUNTED == state
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the result is for the image selection request
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data

            // Load the selected image into the ImageView using Glide
            Glide.with(this)
                .load(imageUri)
                .into(imageView)
        }
    }


    private fun inferenceWithInt8Model(sourceImage: TensorImage): TensorImage {
        // Create an instance of the WhiteboxCartoonGanInt8 model
        val model = WhiteboxCartoonGanInt8.newInstance(this)

        // Run model inference and get the result
        val outputs = model.process(sourceImage)
        val cartoonizedImage = outputs.cartoonizedImageAsTensorImage

        // Release model resources if no longer used
        model.close()

        return cartoonizedImage
    }


    /**
     * Extension function to convert a Drawable to a TensorImage.
     */
    private fun Drawable.toTensorImage(): TensorImage {
        // Convert the Drawable to a Bitmap
        val bitmap = (this as? BitmapDrawable)?.bitmap
            ?: Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)

        // Create a TensorImage from the Bitmap
        return TensorImage.fromBitmap(bitmap)
    }

}



/** commenting out this section of code because it is not currently in use, if in future anyone wants to
     * to use it please comment out this section.
     */
    /**
    private fun inferenceWithFp16Model(sourceImage: TensorImage): TensorImage {
        val model = WhiteboxCartoonGanFp16.newInstance(this)

        // Runs model inference and gets result.
        val outputs = model.process(sourceImage)
        val cartoonizedImage = outputs.cartoonizedImageAsTensorImage

        // Releases model resources if no longer used.
        model.close()

        return cartoonizedImage
    }

    private fun inferenceWithDrModel(sourceImage: TensorImage): TensorImage {
        val model = WhiteboxCartoonGanDr.newInstance(this)

        // Runs model inference and gets result.
        val outputs = model.process(sourceImage)
        val cartoonizedImage = outputs.cartoonizedImageAsTensorImage

        // Releases model resources if no longer used.
        model.close()

        return cartoonizedImage
    }
    */

