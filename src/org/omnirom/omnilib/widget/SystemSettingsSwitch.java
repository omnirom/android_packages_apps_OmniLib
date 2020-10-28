/*
 * Copyright (C) 2020 The OmniROM Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.omnirom.omnilib.widget;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Switch;

public class SystemSettingsSwitch extends Switch {

    private boolean mInflated = true;

    public SystemSettingsSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SystemSettingsSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SystemSettingsSwitch(Context context) {
        super(context, null);
    }

    @Override
    protected void onFinishInflate() {
        mInflated = true;
        setChecked(Settings.System.getInt(getContext().getContentResolver(),
                getResources().getResourceEntryName(getId()), 0) != 0);
    }

    @Override
    public void setChecked(boolean checked) {
        if (!mInflated) return;
        Settings.System.putInt(getContext().getContentResolver(),
                getResources().getResourceEntryName(getId()), checked ? 1 : 0);
        super.setChecked(checked);
    }
}
