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
import android.content.res.TypedArray;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Switch;

import org.omnirom.omnilib.R;

public class SystemSettingsSwitch extends Switch {

    private boolean mInflated = false;
    private int mInitialValue = 0;

    public SystemSettingsSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setInitialValue(context, attrs);
    }

    public SystemSettingsSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInitialValue(context, attrs);
    }

    public SystemSettingsSwitch(Context context) {
        super(context, null);
    }

    private void setInitialValue(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        final TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.SystemSettingsSwitch, 0, 0);

        mInitialValue =
                attributes.getInteger(R.styleable.SystemSettingsSwitch_initialValue, 0);
    }

    @Override
    protected void onFinishInflate() {
        mInflated = true;
        switch (mInitialValue) {
            case 0:
                setChecked(Settings.System.getIntForUser(getContext().getContentResolver(),
                    getResources().getResourceEntryName(getId()), 0, UserHandle.USER_CURRENT) != 0);
                break;
            case 1:
                setChecked(Settings.System.getIntForUser(getContext().getContentResolver(),
                    getResources().getResourceEntryName(getId()), 1, UserHandle.USER_CURRENT) == 1);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if (!mInflated) return;
        Settings.System.putIntForUser(getContext().getContentResolver(),
                getResources().getResourceEntryName(getId()), checked ? 1 : 0, UserHandle.USER_CURRENT);
        super.setChecked(checked);
    }
}
