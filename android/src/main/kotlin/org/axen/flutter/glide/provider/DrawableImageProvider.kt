package org.axen.flutter.glide.provider

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import org.axen.flutter.glide.common.AbstractProvider

class DrawableImageProvider(context: Context): AbstractProvider<Drawable>(context) {

    override fun build(): RequestBuilder<Drawable> {
        return Glide.with(context).asDrawable()
    }
}