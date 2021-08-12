package org.axen.flutter.glide.common

import android.content.Context
import com.bumptech.glide.RequestBuilder
import java.io.File

interface ImageConverter<T> {
    fun convert(
        context: Context,
        builder: RequestBuilder<T>,
        resource: Any
    ): RequestBuilder<T>
}