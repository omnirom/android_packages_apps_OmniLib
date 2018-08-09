/*
 *  Copyright (C) 2018 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.omnirom.omnilib.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.util.Log;

import org.omnirom.omnilib.R;
import org.omnirom.omnilib.utils.AppUtils;

import java.util.List;

public class AppChooser {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private static final String TAG = "AppChooser";
    private static final boolean DEBUG = false;
    private static final int ANIM_DURATION = 300;
    private static final Interpolator FAST_OUT_SLOW_IN = new PathInterpolator(0.4f, 0f, 0.2f, 1f);
    private static boolean mOverlayShown;

    private WindowManager mWindowManager;
    private View mFloatingWidget = null;
    private PackageManager mPm;
    private Handler mHandler = new Handler();
    private int mPosition;
    private int mTimeout;
    private int mOverlayWidth;
    private Context mContext;
    private List<String> mAppList;

    private Runnable mCloseRunnable = new Runnable() {
        @Override
        public void run() {
            if (mOverlayShown) {
                slideAnimation(false);
            }
        }
    };

    public AppChooser(Context context, int position, int timeout, int overlay_width, List<String> app_list) {
        mContext = context;
        mPosition = position;
        mTimeout = timeout;
        mOverlayWidth = overlay_width;
        mAppList = app_list;
        mPm = context.getApplicationContext().getPackageManager();
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public void openDialog() {
        if (DEBUG) Log.d(TAG, "Runing app chooser");

        final LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(
                mContext, android.R.style.Theme_DeviceDefault_Light_Dialog));
        mFloatingWidget = inflater.inflate(R.layout.layout_floating_widget, null);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.x = mContext.getResources().getDimensionPixelSize(R.dimen.floating_widget_window_padding);

        // Selected apps
        LinearLayout linearLayout = (LinearLayout) mFloatingWidget.findViewById(R.id.selected_apps);
        if (linearLayout.getChildCount() > 0) linearLayout.removeAllViews();

        for (final String value : mAppList) {
            try {
                View v = inflater.inflate(R.layout.app_grid_item, null);
                if (DEBUG) Log.d(TAG, "Search icon for : " + value);
                ComponentName componentName = ComponentName.unflattenFromString(value);
                Drawable icon = mPm.getActivityIcon(componentName);
                ((ImageView) v.findViewById(R.id.appIcon)).setImageDrawable(icon);
                v.setPadding(30, 15, 30, 15);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mHandler.removeCallbacks(mCloseRunnable);
                        mOverlayShown = false;
                        try {
                            mWindowManager.removeViewImmediate(mFloatingWidget);
                        } catch (Exception e) {
                            Log.e(TAG, "openApp ", e);
                        }
                        mContext.startActivityAsUser(AppUtils.createIntent(value), UserHandle.CURRENT);
                    }
                });
                linearLayout.addView(v);
            } catch (Exception e) {
                Log.e(TAG, "Set app icon", e);
            }
        }

        // Close button
        View close = inflater.inflate(R.layout.app_grid_item, null);
        close.setPadding(30, 15, 30, 15);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mCloseRunnable);
                slideAnimation(false);
            }
        });

        // Position and close icon
        if (mPosition == LEFT) {
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
            ((ImageView) close.findViewById(R.id.appIcon)).setImageResource(R.drawable.ic_chevron_left);
        } else {
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
            ((ImageView) close.findViewById(R.id.appIcon)).setImageResource(R.drawable.ic_chevron_right);
        }

        linearLayout.addView(close);

        mWindowManager.addView(mFloatingWidget, params);
        slideAnimation(true);

        if (mTimeout > 0) {
            mHandler.postDelayed(mCloseRunnable, mTimeout * 1000);
        }
    }

    private void slideAnimation(boolean show) {
        if (show) {
            int startValue = 0;
            if (mPosition == RIGHT) {
                startValue = mOverlayWidth;
            } else {
                startValue = -mOverlayWidth;
            }
            mFloatingWidget.setTranslationX(startValue);
            mFloatingWidget.setAlpha(0);
            mFloatingWidget.animate()
                    .alpha(1)
                    .translationX(0)
                    .setDuration(ANIM_DURATION)
                    .setInterpolator(FAST_OUT_SLOW_IN)
                    .start();

        } else {
            int endValue = 0;
            if (mPosition == RIGHT) {
                endValue = mOverlayWidth;
            } else {
                endValue = -mOverlayWidth;
            }
            mFloatingWidget.setTranslationX(0);
            mFloatingWidget.setAlpha(1);
            mFloatingWidget.animate()
                    .alpha(0)
                    .translationX(endValue)
                    .setDuration(ANIM_DURATION)
                    .setInterpolator(FAST_OUT_SLOW_IN)
                    .withEndAction(() -> {
                        mOverlayShown = false;
                        try {
                            mWindowManager.removeViewImmediate(mFloatingWidget);
                        } catch (Exception e) {
                            Log.e(TAG, "slideAnimation close ", e);
                        }
                    })
                    .start();
        }
    }
}
