package org.axen.flutter.glide

import android.content.Context
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.view.TextureRegistry
import org.axen.flutter.glide.common.GlideImage
import org.axen.flutter.glide.common.ImageRenderer
import org.axen.flutter.glide.enum.BoxFit
import org.axen.flutter.glide.enum.Resource
import org.axen.flutter.glide.renderer.SurfaceImageRenderer

/** FlutterGlidePlugin */
class FlutterGlidePlugin: FlutterPlugin, MethodCallHandler {

  private lateinit var context: Context
  private lateinit var channel : MethodChannel
  private lateinit var textureRegistry: TextureRegistry
  private lateinit var rendererMap: MutableMap<Int, ImageRenderer<*>>

  override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(binding.binaryMessenger, "flutter_glide")
    channel.setMethodCallHandler(this)
    rendererMap = mutableMapOf()
    context = binding.applicationContext
    textureRegistry = binding.textureRegistry
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "load") {
      load(call, result)
    } else {
      result.notImplemented()
    }
  }

  private fun load(@NonNull call: MethodCall, @NonNull result: Result) {
    val textureId = call.argument<Int>("textureId")
    // TODO 支持OPENGL ES渲染
    val renderer = if (textureId == null || !rendererMap.containsKey(textureId)) {
      val entry = textureRegistry.createSurfaceTexture()
      SurfaceImageRenderer(context, entry)
        .apply { rendererMap[entry.id().toInt()] = this }
    } else rendererMap[textureId]
    val info = GlideImage.obtain().apply {
      resource = call.argument<String>("resource")
      resourceType = Resource.values()[call.argument<Int>("resourceType")!!]
      scaleRatio = call.argument<Double>("scaleRatio")!!
      width = call.argument<Double>("width")!!.toInt()
      height = call.argument<Double>("height")!!.toInt()
      fit = BoxFit.values()[call.argument<Int>("fit")!!]
    }
    renderer!!.render(info, result)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    rendererMap.forEach{ it.value.release() }
    rendererMap.clear()
  }
}
