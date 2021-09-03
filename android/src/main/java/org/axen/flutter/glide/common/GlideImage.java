package org.axen.flutter.glide.common;

import org.axen.flutter.glide.constant.BoxFit;
import org.axen.flutter.glide.constant.Resource;


public class GlideImage {
    private Object resource = null;
    private Resource resourceType = Resource.NONE;
    private double scaleRatio = 3.0;
    private BoxFit fit = BoxFit.COVER;
    private int width = 0;
    private int height = 0;

    public static GlideImage obtain() {
        return GlideImagePool.obtain();
    }

    public void recycle() {
        GlideImagePool.recycle(this);
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    public double getScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(double scaleRatio) {
        this.scaleRatio = scaleRatio;
    }

    public BoxFit getFit() {
        return fit;
    }

    public void setFit(BoxFit fit) {
        this.fit = fit;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
