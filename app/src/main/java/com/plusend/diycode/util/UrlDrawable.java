package com.plusend.diycode.util;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class UrlDrawable extends Drawable implements Drawable.Callback {

    private Drawable mDrawable;

    @Override public void draw(Canvas canvas) {
        if (mDrawable != null) {
            mDrawable.draw(canvas);
        }
    }

    @Override public void setAlpha(int alpha) {
        if (mDrawable != null) {
            mDrawable.setAlpha(alpha);
        }
    }

    @Override public void setColorFilter(ColorFilter cf) {
        if (mDrawable != null) {
            mDrawable.setColorFilter(cf);
        }
    }

    @Override public int getOpacity() {
        if (mDrawable != null) {
            return mDrawable.getOpacity();
        }
        return 0;
    }

    public void setDrawable(Drawable drawable) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        drawable.setCallback(this);
        this.mDrawable = drawable;
    }

    @Override public void invalidateDrawable(Drawable who) {
        if (getCallback() != null) {
            getCallback().invalidateDrawable(who);
        }
    }

    @Override public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (getCallback() != null) {
            getCallback().scheduleDrawable(who, what, when);
        }
    }

    @Override public void unscheduleDrawable(Drawable who, Runnable what) {
        if (getCallback() != null) {
            getCallback().unscheduleDrawable(who, what);
        }
    }
}