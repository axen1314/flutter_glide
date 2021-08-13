package org.axen.flutter.glide.common

import android.content.Context
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.axen.flutter.glide.converter.AssetConverter
import org.axen.flutter.glide.converter.DrawableConverter
import org.axen.flutter.glide.converter.FileConverter
import org.axen.flutter.glide.converter.NetworkConverter
import org.axen.flutter.glide.enum.BoxFit
import org.axen.flutter.glide.enum.Resource

abstract class AbstractProvider<T>(protected val context: Context) : ImageProvider<T> {

    private val converters: MutableMap<Resource, ImageConverter<T>> = mutableMapOf()

    init { register() }

    private fun register() {
        converters[Resource.FILE] = FileConverter()
        converters[Resource.NETWORK] = NetworkConverter()
        converters[Resource.DRAWABLE] = DrawableConverter()
        converters[Resource.ASSET] = AssetConverter()
    }

    override fun provide(info: GlideImage): T {
        val converter = converters[info.resourceType]
            ?: throw RuntimeException("Not support resource type!")
        val request = converter.convert(context, fit(build(), info.fit), info.resource!!)
        val futureTarget = if (info.width > 0 && info.height > 0) {
            val w = info.width * info.scaleRatio
            val h = info.height * info.scaleRatio
            request.submit(w.toInt(), h.toInt())
        } else request.submit()

        return futureTarget.get()
    }

    abstract fun build(): RequestBuilder<T>

    companion object {
        fun <T> fit(builder: RequestBuilder<T>, fit: BoxFit): RequestBuilder<T> {
            return when(fit) {
                BoxFit.COVER -> builder.centerCrop()
                BoxFit.CONTAIN,
                BoxFit.FIT_WIDTH,
                BoxFit.FIT_HEIGHT,
                BoxFit.SCALE_DOWN -> builder.fitCenter()
                else -> builder
            }
        }
    }
}

interface ImageProvider<T> {
    fun provide(info: GlideImage) : T
}