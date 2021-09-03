package org.axen.flutter.glide.common;

import org.axen.flutter.glide.constant.BoxFit;
import org.axen.flutter.glide.constant.Resource;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GlideImagePool {
    private static final ConcurrentLinkedQueue<GlideImage> POOL = new ConcurrentLinkedQueue<>();

    public static GlideImage obtain() {
        GlideImage info = POOL.poll();
        return info != null ? info : new GlideImage();
    }

    public static void recycle(GlideImage info) {
        info.setWidth(0);
        info.setHeight(0);
        info.setResource(null);
        info.setResource(Resource.NONE);
        info.setFit(BoxFit.COVER);
        info.setScaleRatio(3.0);
        POOL.add(info);
    }
}
