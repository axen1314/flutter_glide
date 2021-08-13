package org.axen.flutter.glide.converter

import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import com.bumptech.glide.RequestBuilder
import org.axen.flutter.glide.common.ImageConverter


class AssetConverter<T>: ImageConverter<T> {

    override fun convert(
        context: Context,
        builder: RequestBuilder<T>,
        resource: Any
    ): RequestBuilder<T> {
        val assetManager: AssetManager = context.assets
        val inputStream = assetManager.open(resource as String)
       return builder.load(BitmapFactory.decodeStream(inputStream))
    }
}