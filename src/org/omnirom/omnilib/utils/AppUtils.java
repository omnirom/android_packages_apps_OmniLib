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

package org.omnirom.omnilib.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

public class AppUtils {
    private static final String TAG = "AppUtils";
    private static final boolean DEBUG = false;

    public static Intent createIntent(String value) {
        ComponentName runnigApp = ComponentName.unflattenFromString(value);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.setComponent(runnigApp);

        return intent;
    }

    public static void closeRunningApp(Context context, String packageName) {
        Context mContext = context;

        if (DEBUG) Log.d(TAG, "Running app: " + packageName);

        final ActivityManager am = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RecentTaskInfo> mTasks =
                am.getRecentTasks(Integer.MAX_VALUE, ActivityManager.RECENT_IGNORE_UNAVAILABLE);

        for (int i = 0; i < mTasks.size(); i++) {
            String name = mTasks.get(i).baseIntent
                    .getComponent().getPackageName();
            if (DEBUG) Log.d(TAG, "Found app: " + name);
            if (name.equals(packageName)) {
                if (DEBUG) Log.d(TAG, "Closing running app");
                am.removeTask(mTasks.get(i).persistentId);
                break;
            }
        }
    }
}