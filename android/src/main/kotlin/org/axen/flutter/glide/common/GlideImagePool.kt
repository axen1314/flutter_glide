package org.axen.flutter.glide.common

import org.axen.flutter.glide.enum.BoxFit
import org.axen.flutter.glide.enum.Resource
import java.util.concurrent.ConcurrentLinkedQueue

object GlideImagePool {
    private val POOL: ConcurrentLinkedQueue<GlideImage> = ConcurrentLinkedQueue()

    fun obtain(): GlideImage {
        val info = POOL.poll()
        return info?: GlideImage()
    }

    fun recycle(info: GlideImage) {
        info.apply {
            width = 0
            height = 0
            resource = null
            resourceType = Resource.NONE
            fit = BoxFit.COVER
            scaleRatio = 3.0
            POOL.add(this)
        }
    }
}