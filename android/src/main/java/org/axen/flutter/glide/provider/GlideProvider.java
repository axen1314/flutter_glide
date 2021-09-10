package org.axen.flutter.glide.provider;

import android.content.Context;
import android.net.Uri;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.FutureTarget;

import org.axen.flutter.texture.constant.BoxFit;
import org.axen.flutter.texture.constant.SourceType;
import org.axen.flutter.texture.entity.NativeImage;
import org.axen.flutter.texture.provider.ImageProvider;
import org.axen.flutter.texture.uri.AssetURIParser;
import org.axen.flutter.texture.uri.DrawableURIParser;
import org.axen.flutter.texture.uri.FileURIParser;
import org.axen.flutter.texture.uri.NetworkURIParser;
import org.axen.flutter.texture.uri.URIParser;

import java.util.HashMap;
import java.util.Map;

public abstract class GlideProvider<T> implements ImageProvider<T> {
    protected Context context;
    private final Map<SourceType, URIParser> parsers = new HashMap<>();

    public GlideProvider(Context context) {
        this.context = context;
        register();
    }

    protected void register() {
        parsers.put(SourceType.FILE, new FileURIParser());
        parsers.put(SourceType.NETWORK, new NetworkURIParser());
        parsers.put(SourceType.DRAWABLE, new DrawableURIParser(context));
        parsers.put(SourceType.ASSET, new AssetURIParser());
    }

    @Override
    public T provide(NativeImage info) throws Exception {
        URIParser parser = parsers.get(info.getSourceType());
        if (parser == null) {
            throw new RuntimeException("Not support source type!");
        }
        Uri uri = parser.parse(info.getSource());
        RequestBuilder<T> request = fit(build(), info.getFit()).load(uri);
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
