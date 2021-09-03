package org.axen.flutter.glide.provider;

import org.axen.flutter.glide.common.GlideImage;

public interface ImageProvider<T> {
    T provide(GlideImage info) throws Exception;
}
