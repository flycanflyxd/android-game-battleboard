package com.battleBoard.framework;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.battleBoard.framework.Graphics;
import com.battleBoard.framework.implementation.AndroidImage;

public class Graphics extends Canvas{
	public static enum ImageFormat {
		ARGB8888, ARGB4444, RGB565
	}
    AssetManager assets;
    Bitmap frameBuffer;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public Graphics(AssetManager assets, Bitmap frameBuffer) {
    	super(frameBuffer);
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.paint = new Paint();
    }

    public Image newImage(String fileName, ImageFormat format) {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;
        
        
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    public void clearScreen(int color) {
        drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    public void drawBackground(Bitmap bitmap) {
    	Matrix matrix = new Matrix();
    	matrix.setScale((float)frameBuffer.getWidth() / (float)bitmap.getWidth(), (float)frameBuffer.getHeight() / (float)bitmap.getHeight());
    	drawBitmap(bitmap, matrix, null);
    }
   
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
