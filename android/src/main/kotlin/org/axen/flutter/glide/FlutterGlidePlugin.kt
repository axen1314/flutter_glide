package org.axen.flutter.glide

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import org.axen.flutter.glide.common.GlideImage
import org.axen.flutter.glide.common.ImageRenderer
import org.axen.flutter.glide.enum.BoxFit
import org.axen.flutter.glide.enum.Resource
import org.axen.flutter.glide.renderer.SurfaceImageRenderer

/** FlutterGlidePlugin */
class FlutterGlidePlugin: FlutterPlugin, MethodCallHandler {

  private lateinit var channel : MethodChannel
  private lateinit var renderer: ImageRenderer<*>

  override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(binding.binaryMessenger, "flutter_glide")
    channel.setMethodCallHandler(this)
    // TODO 支持OPENGL ES渲染
    renderer = SurfaceImageRenderer(binding.applicationContext, binding.textureRegistry.createSurfaceTexture())
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "create") {
      val info = GlideImage.obtain().apply {
        resource = call.argument<String>("resource")
        resourceType = Resource.values()[call.argument<Int>("resourceType")!!]
        scaleRatio = call.argument<Double>("scaleRatio")!!
        width = call.argument<Double>("width")!!.toInt()
        height = call.argument<Double>("height")!!.toInt()
        fit = BoxFit.values()[call.argument<Int>("fit")!!]
      }
      renderer.render(info, result)
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    renderer.release()
  }
}
