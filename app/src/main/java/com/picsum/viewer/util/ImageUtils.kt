package com.picsum.viewer.util

import android.content.Context
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.picsum.viewer.R

object ImageUtils {

    fun getImageUrl(
        context: Context,
        id: Int,
        blur: Boolean? = false,
        grayScale: Boolean? = false
    ): String {
        val width =
            context.resources.displayMetrics.widthPixels / context.resources.getInteger(R.integer.columns)
        var imageurl = "https://picsum.photos/id/${id}/${width}"
        if (blur == true || grayScale == true) {
            imageurl = imageurl.plus("?")
            if (blur == true) {
                imageurl = imageurl.plus("blur")
                if (grayScale == true) {
                    imageurl = imageurl.plus("&")
                    imageurl = imageurl.plus("grayscale")
                }
            } else {
                if (grayScale == true) {
                    imageurl = imageurl.plus("grayscale")
                }
            }
        }
        return imageurl
    }

    fun loadImage(
        imageView: ImageView,
        id: Int,
        blur: Boolean? = false,
        grayScale: Boolean? = false
    ) {
        getImageUrl(imageView.context, id, blur, grayScale).let {
            val imgUri = it.toUri().buildUpon().scheme("https").build()
            Glide.with(imageView)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_downloading)
                        .error(R.drawable.ic_error)
                )
                .into(imageView)
        }
    }
}