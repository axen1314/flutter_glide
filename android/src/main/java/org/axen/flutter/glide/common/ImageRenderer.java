package org.axen.flutter.glide.common;

import io.flutter.plugin.common.MethodChannel;

public interface ImageRenderer<T> {
    void render(GlideImage info, MethodChannel.Result result);
    void release();
}
