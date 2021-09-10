package org.axen.flutter.glide.provider;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

public final class GlideDrawableProvider extends GlideProvider<Drawable> {

    public GlideDrawableProvider(@NonNull Context context) {
        super(context);
    }

    @NonNull
    protected RequestBuilder<Drawable> build() {
        return Glide.with(this.context).asDrawable();
    }

}
