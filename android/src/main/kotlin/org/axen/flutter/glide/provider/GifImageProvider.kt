package org.axen.flutter.glide.provider

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.gif.GifDrawable
import org.axen.flutter.glide.common.AbstractProvider

class GifImageProvider(context: Context): AbstractProvider<GifDrawable>(context) {

    override fun build(): RequestBuilder<GifDrawable> {
        return Glide.with(context).asGif()
    }
}