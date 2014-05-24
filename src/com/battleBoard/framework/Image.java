package com.battleBoard.framework;

import android.graphics.Bitmap;

import com.battleBoard.framework.Graphics.ImageFormat;

public interface Image {
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
    public Bitmap getBitmap();
}
