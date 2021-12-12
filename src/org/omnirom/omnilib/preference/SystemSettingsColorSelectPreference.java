/*
 * Copyright (C) 2021 The OmniROM project
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

package org.omnirom.omnilib.preference;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;

public class SystemSettingsColorSelectPreference extends ColorSelectPreference {
    private int mDefaultColor = ColorSelectPreference.DEFAULT_COLOR;

    public SystemSettingsColorSelectPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SystemSettingsColorSelectPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SystemSettingsColorSelectPreference(Context context) {
        super(context, null);
    }

    @Override
    protected boolean persistInt(int value) {
        if (!shouldPersist()) {
            return false;
        }

        if (value == getPersistedInt(~value)) {
            // It's already there, so the same as persisting
            return true;
        }

        Settings.System.putInt(getContext().getContentResolver(), getKey(), value);
        return true;
    }

    @Override
    protected int getPersistedInt(int defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }

        return Settings.System.getInt(getContext().getContentResolver(),
                getKey(), defaultReturnValue);
    }

    public void updateColor() {
        super.setColor(getPersistedInt(mColorValue));
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        persistInt(color);
    }

    @Override
    public void setDefaultValue(Object defaultValue) {
        mDefaultColor = (Integer) defaultValue;
        super.setDefaultValue(defaultValue);
        super.setColor(getPersistedInt((Integer) defaultValue));
    }

    public void resetToDefaultValue() {
        setColor(mDefaultColor);
    }
}
