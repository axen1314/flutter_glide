package org.axen.flutter.glide.provider;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

public final class BitmapImageProvider extends AbstractProvider<Bitmap> {

    public BitmapImageProvider(@NonNull Context context) {
        super(context);
    }

    @NonNull
    protected RequestBuilder<Bitmap> build() {
        return Glide.with(this.context).asBitmap();
    }
}
