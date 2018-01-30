package com.mvvm.core.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ScaleXSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ViewUtils {

    /**
     * Hides the soft keyboard when focus is gained
     *
     * @param view
     */
    public static void setSoftInputHideFocusListener(View view) {
        view.setClickable(true);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    InputMethodManager imm = (InputMethodManager) view.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    /**
     * Closes the soft keyboard with a simple context.
     *
     * @param context
     */
    public static void closeSoftInput(Context context) {
        if (context instanceof Activity) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = ((Activity) context).getCurrentFocus();
            if (currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
    }

    /**
     * Closes the soft keyboard based on a view.
     * <p>
     * This method was created to support dialogfragment where the getCurrentFocus() from the above given context was
     * always returning null thus not closing the keyboard.
     *
     * @param view
     */
    public static void closeSoftInput(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Prevents soft keyboard from appearing on the first edittext
     */
    public static void preventSoftInputOpen(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * Focus on the passed view and open the soft keyboard
     *
     * @param view
     */
    public static void focusAndShowSoftInput(final View view) {
        view.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }


    /**
     * Stretch a view to match the device screen width, and resize the height to fit the ratio of the passed Drawable
     */
    public static void applyDrawableRatio(View view, Drawable drawable, Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        float bitmapRatio = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
        int drawableHeight = (int) Math.floor((dm.widthPixels) / bitmapRatio);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = dm.widthPixels;
        layoutParams.height = drawableHeight;

        view.setLayoutParams(layoutParams);

    }

    /**
     * Temporarily disable a view to block double taps
     */
    public static void blockDoubleClicks(final View view) {
        blockDoubleClicks(view, 200);
    }

    /**
     * Temporarily disable a view to block double taps for the specified duration
     */
    public static void blockDoubleClicks(final View view, long duration) {
        if(view!= null) {
            view.setClickable(false);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        view.setClickable(true);
                    } catch (Exception e) {
                        // If the view is in a weird state, we do nothing
                    }
                }
            }, duration);
        }
    }
}
