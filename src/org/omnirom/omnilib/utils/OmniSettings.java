/*
* Copyright (C) 2022 The OmniROM Project
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
package org.omnirom.omnilib.utils;

import android.provider.BaseColumns;
import android.provider.Settings.NameValueTable;
import android.util.ArrayMap;

import java.util.Map;

public class OmniSettings extends NameValueTable implements BaseColumns {

        public static final String OMNI_SYSTEM_PROXI_CHECK_ENABLED = "system_proxi_check_enabled";

        /**
         * Enable proxi check for wake keys - must be implemented in a device
         * KeyHandler
         */
        public static final String OMNI_DEVICE_PROXI_CHECK_ENABLED = "device_proxi_check_enabled";

        /**
         * some devices have a extra hw button e.g. n3 on the back on the
         * fingerprint sensor. allow mapping button to key
         *
         */
        public static final String OMNI_BUTTON_EXTRA_KEY_MAPPING = "button_extra_mapping";

        public static final String OMNI_DEVICE_FEATURE_SETTINGS = "device_feature_settings";

        public static final String OMNI_LONG_PRESS_POWER_TORCH = "long_press_power_torch";

        public static final String OMNI_ADVANCED_REBOOT = "advanced_reboot";

        /**
         * SettingsBackupAgent will combine its list with this so we dont need
         * to add new things into SettingsProvider SystemSettings
         */
        public static final String[] OMNI_SETTINGS_TO_BACKUP = {
            // OMNI_FOO_BAR_BALABALA
            OMNI_SYSTEM_PROXI_CHECK_ENABLED,
            OMNI_DEVICE_PROXI_CHECK_ENABLED,
            OMNI_BUTTON_EXTRA_KEY_MAPPING,
            OMNI_DEVICE_FEATURE_SETTINGS,
            OMNI_LONG_PRESS_POWER_TORCH,
            OMNI_ADVANCED_REBOOT,
        };

        /**
         * SettingsBackupAgent will combine its list with this so we dont need
         * to add new things into SettingsProvider SystemSettingsValidators
         * we cant use Validators interface so use a simple integer mapping
         * BOOLEAN_VALIDATOR == 0
         * ANY_INTEGER_VALIDATOR == 1
         * ANY_STRING_VALIDATOR == 2
         */
        public static final Map<String, Integer> OMNI_SETTINGS_VALIDATORS = new ArrayMap<>();
        static {
            //OMNI_SETTINGS_VALIDATORS.put(OMNI_FOO_BAR_BALABALA, 1);
            OMNI_SETTINGS_VALIDATORS.put(OMNI_SYSTEM_PROXI_CHECK_ENABLED, 0);
            OMNI_SETTINGS_VALIDATORS.put(OMNI_DEVICE_PROXI_CHECK_ENABLED, 0);
            OMNI_SETTINGS_VALIDATORS.put(OMNI_BUTTON_EXTRA_KEY_MAPPING, 2);
            OMNI_SETTINGS_VALIDATORS.put(OMNI_DEVICE_FEATURE_SETTINGS, 2);
            OMNI_SETTINGS_VALIDATORS.put(OMNI_LONG_PRESS_POWER_TORCH, 0);
            OMNI_SETTINGS_VALIDATORS.put(OMNI_ADVANCED_REBOOT, 0);
        }
}
