package org.axen.flutter.glide.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.view.Surface;

import androidx.annotation.VisibleForTesting;

import org.axen.flutter.glide.common.AbstractImageRenderer;
import org.axen.flutter.glide.common.GlideImage;
import org.axen.flutter.glide.provider.BitmapImageProvider;
import org.axen.flutter.glide.provider.ImageProvider;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.TextureRegistry;

public class SurfaceImageRenderer extends AbstractImageRenderer<Bitmap> {

    public SurfaceImageRenderer(
            Context context,
            TextureRegistry.SurfaceTextureEntry textureEntry
    ) {
        super(textureEntry, new BitmapImageProvider(context));
    }

    public SurfaceImageRenderer(
            TextureRegistry.SurfaceTextureEntry textureEntry,
            ImageProvider<Bitmap> provider
    ) {
        super(textureEntry, provider);
    }

    private Surface surface;

    @Override
    public void release() {
        textureEntry.release();
        if (surface != null) surface.release();
    }

    @Override
    protected void onDraw(Bitmap image, GlideImage info, MethodChannel.Result result) {
        SurfaceTexture texture = textureEntry.surfaceTexture();
        if (surface == null) surface = new Surface(texture);
        if (surface.isValid()) {
            double width = image.getWidth();
            double height = image.getHeight();
            texture.setDefaultBufferSize((int) width, (int) height);
            draw(surface, image);
            Map<String, Object> map = new HashMap<>();
            map.put("textureId",  textureEntry.id());
            double scaleRatio = info.getScaleRatio();
            map.put("width", width / scaleRatio);
            map.put("height", height / scaleRatio);
            postSuccess(result, map);
        } else {
            postError(result, "Surface is invalid!");
        }
    }

    @VisibleForTesting
    public static void draw(Surface surface, Bitmap image) {
        Rect dstRect = new Rect(0, 0, image.getWidth(), image.getHeight());
        Canvas canvas = surface.lockCanvas(dstRect);
        // Fixed: PNG图片背景默认显示为白色的问题
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(image, null, dstRect, null); //图片的绘制
        surface.unlockCanvasAndPost(canvas);
    }
}
