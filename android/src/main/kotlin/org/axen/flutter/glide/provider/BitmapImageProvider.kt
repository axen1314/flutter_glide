package org.axen.flutter.glide.provider

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import org.axen.flutter.glide.common.AbstractProvider

class BitmapImageProvider(context: Context): AbstractProvider<Bitmap>(context) {

    override fun build(): RequestBuilder<Bitmap> {
        return Glide.with(context).asBitmap()
    }
}