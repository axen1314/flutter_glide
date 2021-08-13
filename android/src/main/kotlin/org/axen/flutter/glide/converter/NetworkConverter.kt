package org.axen.flutter.glide.converter

import android.content.Context
import com.bumptech.glide.RequestBuilder
import org.axen.flutter.glide.common.ImageConverter

class NetworkConverter<T> : ImageConverter<T> {
    override fun convert(
        context: Context,
        builder: RequestBuilder<T>,
        resource: Any
    ): RequestBuilder<T> {
        return builder.load(resource as String)
    }
}