package org.axen.flutter.glide.converter

import android.content.Context
import com.bumptech.glide.RequestBuilder
import org.axen.flutter.glide.common.ImageConverter

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