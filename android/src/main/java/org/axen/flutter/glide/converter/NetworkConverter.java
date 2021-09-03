package org.axen.flutter.glide.converter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestBuilder;

public final class NetworkConverter<T> implements ImageConverter<T> {
    @NonNull
    public RequestBuilder<T> convert(@NonNull Context context, @NonNull RequestBuilder<T> builder, @NonNull Object resource) {
        return builder.load((String)resource);
    }
}
