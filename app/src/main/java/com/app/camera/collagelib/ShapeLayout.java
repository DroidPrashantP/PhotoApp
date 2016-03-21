package com.app.camera.collagelib;

public class ShapeLayout {
    boolean isScalable;
    int porterDuffClearBorderIntex;
    Shape[] shapeArr;

    public ShapeLayout() {
        this.isScalable = false;
        this.porterDuffClearBorderIntex = -1;
    }

    public ShapeLayout(Shape[] arr) {
        this.isScalable = false;
        this.porterDuffClearBorderIntex = -1;
        this.shapeArr = arr;
    }

    public void setClearIndex(int index) {
        if (index >= 0 && index < this.shapeArr.length) {
            this.porterDuffClearBorderIntex = index;
        }
    }

    public void setScalibility(boolean scalebility) {
        this.isScalable = scalebility;
    }

    public int getClearIndex() {
        return this.porterDuffClearBorderIntex;
    }

    public boolean getScalibility() {
        return this.isScalable;
    }
}
