package org.axen.flutter.glide.provider;

import android.content.Context;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.FutureTarget;

import org.axen.flutter.glide.common.GlideImage;
import org.axen.flutter.glide.converter.ImageConverter;
import org.axen.flutter.glide.constant.BoxFit;
import org.axen.flutter.glide.constant.Resource;
import org.axen.flutter.glide.converter.AssetConverter;
import org.axen.flutter.glide.converter.DrawableConverter;
import org.axen.flutter.glide.converter.FileConverter;
import org.axen.flutter.glide.converter.NetworkConverter;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractProvider<T> implements ImageProvider<T> {
    protected Context context;
    private final Map<Resource, ImageConverter<T>> converters = new HashMap<>();

    public AbstractProvider(Context context) {
        this.context = context;
        register();
    }

    private void register() {
        converters.put(Resource.FILE, new FileConverter<T>());
        converters.put(Resource.NETWORK, new NetworkConverter<T>());
        converters.put(Resource.DRAWABLE, new DrawableConverter<T>());
        converters.put(Resource.ASSET, new AssetConverter<T>());
    }

    @Override
    public T provide(GlideImage info) throws Exception {
        ImageConverter<T> converter = converters.get(info.getResourceType());
        if (converter == null) {
            throw new RuntimeException("Not support resource type!");
        }
        RequestBuilder<T> request = converter.convert(context, fit(build(), info.getFit()), info.getResource());
        FutureTarget<T> futureTarget;
        double width = info.getWidth();
        double height = info.getHeight();
        if (width > 0 && height > 0) {
            double scaleRatio = info.getScaleRatio();
            double w = width * scaleRatio;
            double h = height * scaleRatio;
            futureTarget = request.submit((int) w, (int) h);
        } else {
            futureTarget = request.submit();
        }

        return futureTarget.get();
    }

    protected abstract RequestBuilder<T> build();

    public static <T> RequestBuilder<T> fit(RequestBuilder<T> builder, BoxFit fit) {
        switch (fit) {
            case COVER:
                return builder.centerCrop();
            case CONTAIN:
            case FIT_WIDTH:
            case FIT_HEIGHT:
            case SCALE_DOWN:
                return builder.fitCenter();
        }
        return builder;
    }
}
