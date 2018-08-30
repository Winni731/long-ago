package com.facundolinlaud.supergame.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by facundo on 3/20/16.
 */
public class Dimensions {
    /* One meter in pixels */
    public static final float PX_PER_METER = 32f;

    /* Meters to pixels */
    public static final float METERS_PER_PX = 1/32f;

    public static final float BOX_2D_OFFSET = 1/2f;

    public static final int SCREEN_WIDTH = 1024;

    public static final int SCREEN_HEIGHT = 768;

    public static final float toMeters(int px){
        return (float) px / PX_PER_METER;
    }

    public static final float toMeters(float px){
        return px / PX_PER_METER;
    }

    public static final int toPixels(int meters){
        return (int) (meters * METERS_PER_PX);
    }

    public static final Vector2 toMeters(Vector2 px){
        return new Vector2(toMeters(px.x), toMeters(px.y));
    }

    public static final Vector2 calculateGlobalPositionInPixelsToMetersRelativeToCenter(Vector2 px){
        float x = px.x - SCREEN_WIDTH / 2;
        float y = px.y - SCREEN_HEIGHT / 2;

        return new Vector2(toMeters(x), toMeters(y));
    }
}
