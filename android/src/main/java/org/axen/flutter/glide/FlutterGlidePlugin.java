package org.axen.flutter.glide;

import android.content.Context;
import android.graphics.SurfaceTexture;

import androidx.annotation.NonNull;

import org.axen.flutter.glide.common.GlideImage;
import org.axen.flutter.glide.common.ImageRenderer;
import org.axen.flutter.glide.constant.BoxFit;
import org.axen.flutter.glide.constant.Resource;
import org.axen.flutter.glide.renderer.SurfaceImageRenderer;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.TextureRegistry;

public class FlutterGlidePlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private static final String CHANNEL = "org.axen.flutter/flutter_glide";

    private Context context;
    private MethodChannel channel;
    private TextureRegistry textureRegistry;
    private Map<Integer, ImageRenderer<?>> rendererMap;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
        rendererMap = new HashMap<>();
        context = binding.getApplicationContext();
        textureRegistry = binding.getTextureRegistry();
        channel = new MethodChannel(binding.getBinaryMessenger(), CHANNEL);
        channel.setMethodCallHandler(this);
    }

    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("load")) {
            load(call, result);
        } else {
            result.notImplemented();
        }
    }

    private void load(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        Integer textureId = call.argument("textureId");
        // TODO 支持OPENGL ES渲染
        ImageRenderer<?> renderer;
        if (textureId == null || !rendererMap.containsKey(textureId)) {
            TextureRegistry.SurfaceTextureEntry entry = textureRegistry.createSurfaceTexture();
            renderer = new SurfaceImageRenderer(context, entry);
            rendererMap.put((int) entry.id(), renderer);
        } else {
            renderer = rendererMap.get(textureId);
        }
        GlideImage info = GlideImage.obtain();
        info.setResource(call.argument("resource"));
        Integer resourceType = call.argument("resourceType");
        if (resourceType != null)
            info.setResourceType(Resource.values()[(int) resourceType]);
        Double scaleRatio = call.argument("scaleRatio");
        if (scaleRatio != null) info.setScaleRatio((double) scaleRatio);
        Double width = call.argument("width");
        if (width != null) info.setWidth(width.intValue());
        Double height = call.argument("height");
        if (height != null) info.setHeight(height.intValue());
        Integer fit = call.argument("fit");
        if (fit != null) info.setFit(BoxFit.values()[(int) fit]);
        if (renderer != null) renderer.render(info, result);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        for(Map.Entry<Integer, ImageRenderer<?>> entry : rendererMap.entrySet())
            entry.getValue().release();
        rendererMap.clear();
    }
}
