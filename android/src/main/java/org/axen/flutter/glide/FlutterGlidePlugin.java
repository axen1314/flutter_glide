package org.axen.flutter.glide;

import android.content.Context;

import org.axen.flutter.glide.provider.GlideBitmapProvider;
import org.axen.flutter.texture.FlutterTexturePlugin;
import org.axen.flutter.texture.constant.SourceType;
import org.axen.flutter.texture.entity.NativeImage;
import org.axen.flutter.texture.renderer.ImageRenderer;
import org.axen.flutter.texture.renderer.SurfaceBitmapRenderer;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.view.TextureRegistry;

public class FlutterGlidePlugin extends FlutterTexturePlugin implements FlutterPlugin {

    @Override
    protected ImageRenderer<NativeImage> getImageRenderer(
            Context context,
            TextureRegistry.SurfaceTextureEntry entry,
            SourceType sourceType
    ) {
        return new SurfaceBitmapRenderer(entry, new GlideBitmapProvider(context));
    }

    @Override
    protected String getChannel() {
        return "org.axen.flutter/flutter_glide";
    }
}
