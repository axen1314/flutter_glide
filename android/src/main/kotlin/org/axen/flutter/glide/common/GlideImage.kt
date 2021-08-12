package org.axen.flutter.glide.common

import org.axen.flutter.glide.enum.BoxFit
import org.axen.flutter.glide.enum.Resource


class GlideImage {
    var resource: Any? = null
    var resourceType: Resource = Resource.NONE
    var scaleRatio: Double = 3.0
    var fit: BoxFit = BoxFit.COVER
    var width: Int = 0
    var height: Int = 0

    companion object {
        fun obtain(): GlideImage {
            return GlideImagePool.obtain()
        }
    }

    fun recycle() {
        GlideImagePool.recycle(this)
    }
}
