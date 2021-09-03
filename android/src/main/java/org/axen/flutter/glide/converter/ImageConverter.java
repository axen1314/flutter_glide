package org.axen.flutter.glide.converter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestBuilder;

import java.io.IOException;


public interface ImageConverter<T> {
    @NonNull
    RequestBuilder<T> convert(
            @NonNull Context context,
            @NonNull RequestBuilder<T> builder,
            @NonNull Object resource
    ) throws Exception;
}

