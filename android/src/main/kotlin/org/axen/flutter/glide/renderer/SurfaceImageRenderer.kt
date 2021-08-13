package org.axen.flutter.glide.renderer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.Surface
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.TextureRegistry
import org.axen.flutter.glide.common.AbstractImageRenderer
import org.axen.flutter.glide.common.GlideImage
import org.axen.flutter.glide.common.ImageProvider
import org.axen.flutter.glide.provider.BitmapImageProvider

class SurfaceImageRenderer(
    context: Context,
    textureEntry: TextureRegistry.SurfaceTextureEntry,
    provider: ImageProvider<Bitmap> = BitmapImageProvider(context)
): AbstractImageRenderer<Bitmap>(textureEntry, provider) {

    private var surface: Surface? = null

    override fun release() {
        surface?.release()
        textureEntry.release()
    }

    override fun onDraw(image: Bitmap, info: GlideImage, result: MethodChannel.Result) {
        val texture = textureEntry.surfaceTexture()
        if (surface == null) surface = Surface(texture)
        if (surface?.isValid == true) {
            val dstRect = Rect(0, 0, image.width, image.height)
            texture.setDefaultBufferSize(image.width, image.height)
            val canvas = surface!!.lockCanvas(dstRect)
            canvas.drawBitmap(image, null, dstRect, null)//图片的绘制
            surface!!.unlockCanvasAndPost(canvas)
            val map = mapOf(
                "textureId" to textureEntry.id(),
                "width" to image.width / info.scaleRatio,
                "height" to image.height / info.scaleRatio
            )
            postSuccess(result, map)
        } else {
            postError(result, errorString =  "Surface is invalid!")
        }
    }
}