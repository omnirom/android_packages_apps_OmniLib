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

import android.content.Context;
import java.util.List;

public class AppChooser {
    private static final String TAG = "AppChooser";

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    public void openAppChooserDialog(final Context context, List<String> appList, int timeout, int position) {
        if (mRecalcOverlayWidth) {
            mOverlayWidth = getOverlayWidth(context);
            mRecalcOverlayWidth = false;
        }

        final LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(
                context, android.R.style.Theme_DeviceDefault_Light_Dialog));
        mFloatingWidget = inflater.inflate(R.layout.layout_floating_widget, null);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.x = context.getResources().getDimensionPixelSize(R.dimen.floating_widget_window_padding);

        // Selected apps
        LinearLayout linearLayout = (LinearLayout) mFloatingWidget.findViewById(R.id.selected_apps);
        if (linearLayout.getChildCount() > 0) linearLayout.removeAllViews();

        for (final String value : appList) {
            try {
                View v = inflater.inflate(R.layout.app_grid_item, null);
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
                        openApp(value, context);
                    }
                });
                linearLayout.addView(v);
            } catch (PackageManager.NameNotFoundException e) {
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
        chooserPosition = getPrefs(context).getInt(EventServiceSettings.APP_CHOOSER_POSITION, LEFT);
        if (chooserPosition == LEFT) {
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
            ((ImageView) close.findViewById(R.id.appIcon)).setImageResource(R.drawable.ic_chevron_left);
        } else {
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
            ((ImageView) close.findViewById(R.id.appIcon)).setImageResource(R.drawable.ic_chevron_right);
        }

        linearLayout.addView(close);

        mWindowManager.addView(mFloatingWidget, params);
        slideAnimation(true);

        if (timeout > 0) {
            mHandler.postDelayed(mCloseRunnable, timeout * 1000);
        }
    }
}
