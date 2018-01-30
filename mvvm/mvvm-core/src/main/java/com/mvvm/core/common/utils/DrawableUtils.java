package com.mvvm.core.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

public class DrawableUtils {

    public static Drawable getTintedDrawable(Context context, @DrawableRes int drawableResId, @ColorRes int colorResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId).mutate();
        drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, colorResId), PorterDuff.Mode.SRC_ATOP));
        return drawable;
    }

    public static Drawable getTintedDrawable(Context context, Drawable d, @ColorRes int colorResId) {
        Drawable drawable = d.mutate();
        drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, colorResId), PorterDuff.Mode.SRC_ATOP));
        return drawable;
    }

}
