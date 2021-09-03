package org.axen.flutter.glide.converter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestBuilder;

import java.lang.reflect.Field;

public final class DrawableConverter<T> implements ImageConverter<T> {
    @NonNull
    public RequestBuilder<T> convert(
            @NonNull Context context,
            @NonNull RequestBuilder<T> builder,
            @NonNull Object resource
    ) throws Exception {
        String[] classGroup = ((String) resource).split("\\.");
        String packageName = context.getPackageName();
        Class clazz = Class.forName(packageName + '.' + classGroup[0] + '$' + classGroup[1]);
        Field field = clazz.getDeclaredField(classGroup[2]);
        Object value = field.get((Object) null);
        return builder.load(value);
    }
}
