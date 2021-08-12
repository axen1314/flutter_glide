package org.axen.flutter.glide.common

import android.content.Context
import com.bumptech.glide.RequestBuilder
import java.io.File

class DrawableConverter<T>: ImageConverter<T> {
    override fun convert(
        context: Context,
        builder: RequestBuilder<T>,
        resource: Any
    ): RequestBuilder<T> {
        val classGroup = (resource as String).split(".")
        val packageName = context.packageName
        val clazz = Class.forName("$packageName.${classGroup[0]}$${classGroup[1]}")
        val field = clazz.getDeclaredField(classGroup[2])
        val value = field.get(null) as Int
        return builder.load(value)
    }

}

class NetworkConverter<T> : ImageConverter<T> {
    override fun convert(
        context: Context,
        builder: RequestBuilder<T>,
        resource: Any
    ): RequestBuilder<T> {
        return builder.load(resource as String)
    }
}

class FileConverter<T> : ImageConverter<T> {
    override fun convert(
        context: Context,
        builder: RequestBuilder<T>,
        resource: Any
    ): RequestBuilder<T> {
        return builder.load(File(resource as String))
    }
}

interface ImageConverter<T> {
    fun convert(
        context: Context,
        builder: RequestBuilder<T>,
        resource: Any
    ): RequestBuilder<T>
}