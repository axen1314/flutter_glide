package org.axen.flutter.glide.example

import android.app.Activity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.axen.flutter.glide.renderer.SurfaceImageRenderer

class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<SurfaceView>(R.id.sv).holder.addCallback(object: SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Thread {
                    val bitmap = Glide
                        .with(applicationContext)
                        .asBitmap()
                        .load(R.drawable.ic_bar_back)
                        .submit(48, 48)
                        .get()
                    SurfaceImageRenderer.draw(holder.surface, bitmap)
                    runOnUiThread {
                        val iv = findViewById<ImageView>(R.id.iv)
                        iv.setImageBitmap(bitmap)
                        iv.invalidate()
                    }
                }.start()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

            }

        })

    }
}