package org.axen.flutter.glide.provider;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;

public final class GifImageProvider extends AbstractProvider<GifDrawable> {

    public GifImageProvider(@NonNull Context context) {
        super(context);
    }

    @NonNull
    protected RequestBuilder<GifDrawable> build() {
        return Glide.with(this.context).asGif();
    }

}
