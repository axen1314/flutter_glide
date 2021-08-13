package org.axen.flutter.glide.converter

import android.content.Context
import com.bumptech.glide.RequestBuilder
import org.axen.flutter.glide.common.ImageConverter
import java.io.File

class FileConverter<T> : ImageConverter<T> {
    override fun convert(
        context: Context,
        builder: RequestBuilder<T>,
        resource: Any
    ): RequestBuilder<T> {
        return builder.load(File(resource as String))
    }
}