package org.axen.flutter.glide.converter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestBuilder;

import java.io.IOException;
import java.io.InputStream;

public final class AssetConverter<T> implements ImageConverter<T> {
    @NonNull
    public RequestBuilder<T> convert(
            @NonNull Context context,
            @NonNull RequestBuilder<T> builder,
            @NonNull Object resource
    ) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open((String)resource);
        return builder.load(BitmapFactory.decodeStream(inputStream));
    }
}
