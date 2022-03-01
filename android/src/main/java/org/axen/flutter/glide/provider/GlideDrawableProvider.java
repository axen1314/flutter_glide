package org.axen.flutter.glide.provider;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import org.axen.flutter.texture.entity.NativeImage;

public final class GlideDrawableProvider extends GlideProvider<Drawable> {

    public GlideDrawableProvider(@NonNull Context context) {
        super(context);
    }

    @NonNull
    protected RequestBuilder<Drawable> build() {
        return Glide.with(this.context).asDrawable();
    }

    @Override
    public Drawable provide(NativeImage info) throws Exception {
        Drawable drawable = super.provide(info);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }
}
