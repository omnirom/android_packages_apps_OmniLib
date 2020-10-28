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
import android.util.TypedValue;
import android.widget.Switch;

import org.omnirom.omnilib.R;

public class SecureSettingsSwitch extends Switch {

    private boolean mInflated = false;
    private AttributeSet mAttrs;
    private int mDefaultValue = 0;
    private Context mContext;

    public SecureSettingsSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAttrs = attrs;
        mContext = context;
    }

    public SecureSettingsSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAttrs = attrs;
        mContext = context;
    }

    public SecureSettingsSwitch(Context context) {
        super(context, null);
    }

    @Override
    protected void onFinishInflate() {
        mInflated = true;

        final TypedArray attributes = mContext.obtainStyledAttributes(mAttrs,
                R.styleable.SecureSettingsSwitch);

        int defAttr =
                attributes.getInt(R.styleable.SecureSettingsSwitch_initialValue);

        if (mAttrs != null) {
            switch (defAttr) {
                case 0:
                    setChecked(Settings.Secure.getIntForUser(getContext().getContentResolver(),
                        getResources().getResourceEntryName(getId()), 0, UserHandle.USER_CURRENT) != 0);
                    break;
               case 1:
                    setChecked(Settings.Secure.getIntForUser(getContext().getContentResolver(),
                        getResources().getResourceEntryName(getId()), 1, UserHandle.USER_CURRENT) == 1);
            }
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if (!mInflated) return;
        Settings.Secure.putIntForUser(getContext().getContentResolver(),
                getResources().getResourceEntryName(getId()), checked ? 1 : 0, UserHandle.USER_CURRENT);
        super.setChecked(checked);
    }
}
