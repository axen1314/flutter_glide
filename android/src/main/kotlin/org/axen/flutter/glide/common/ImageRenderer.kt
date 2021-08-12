package org.axen.flutter.glide.common

import android.os.Handler
import android.os.Looper
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.TextureRegistry
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class AbstractImageRenderer<T>(
    protected val textureEntry: TextureRegistry.SurfaceTextureEntry,
    private val provider: ImageProvider<T>
): ImageRenderer<T> {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun render(info: GlideImage, result: MethodChannel.Result) {
        executor.execute {
            try {
                val image = provider.provide(info)
                onDraw(image, info, result)
            } catch (e: Exception) {
                onFail(e)
                postError(result, errorString = e.message)
            } finally {
                onComplete()
                info.recycle()
            }
        }
    }

    protected fun postSuccess(
        result: MethodChannel.Result,
        map: Map<String, Any>
    ) {
        handler.post{ result.success(map) }
    }

    protected fun postError(
        result: MethodChannel.Result,
        errorCode: String = "-1",
        errorString: String?,
        errorDetail: String = ""
    ) {
        handler.post{ result.error(errorCode, errorString, errorDetail) }
    }

    protected open fun onComplete() {}

    protected open fun onFail(error: Exception) {}

    abstract fun onDraw(image: T, info: GlideImage, result: MethodChannel.Result)

}

interface ImageRenderer<T> {
    fun render(info: GlideImage, result: MethodChannel.Result)
    fun release()
}