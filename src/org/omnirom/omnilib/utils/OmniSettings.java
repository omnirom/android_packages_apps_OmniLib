/*
* Copyright (C) 2023 The OmniROM Project
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

    /**
     Add omni settings like e.g.
     @hide
     public static final String OMNI_FOO_BAR_BALABALA = "foo_bar_balabala";
    */

    /**
     * @hide
     */
    public static final String OMNI_SYSTEM_PROXI_CHECK_ENABLED = "system_proxi_check_enabled";

    /**
     * Enable proxi check for wake keys - must be implemented in a device
     * KeyHandler
     * @hide
     */
    public static final String OMNI_DEVICE_PROXI_CHECK_ENABLED = "device_proxi_check_enabled";

    /**
     * some devices have a extra hw button e.g. n3 on the back on the
     * fingerprint sensor. allow mapping button to key
     *
     * @hide
     */
    public static final String OMNI_BUTTON_EXTRA_KEY_MAPPING = "button_extra_mapping";

    /**
     * @hide
     */
    public static final String OMNI_DEVICE_FEATURE_SETTINGS = "device_feature_settings";

    /**
     * @hide
     */
    public static final String OMNI_LONG_PRESS_POWER_TORCH = "long_press_power_torch";

    /**
     * @hide
     */
    public static final String OMNI_ADVANCED_REBOOT = "advanced_reboot";

    /**
     * @hide
     */
    public static final String OMNI_MONET_DISABLE = "monet_disable";

    /**
     * @hide
     */
    public static final String OMNI_SHOW_BATTERY_IMAGE =
        "show_battery_image";

    /**
     * @hide
     */
    public static final String OMNI_LOCKSCREEN_WEATHER_ENABLED = "lockscreen_weather_enabled";

    /** @hide */
    public static final String OMNI_BACK_GESTURE_HEIGHT = "back_gesture_height";

    /**
     * @hide
     */
    public static final String OMNI_STATUS_BAR_ALARM = "status_bar_alarm";

    /**
     * @hide
     */
    public static final String OMNI_ENABLE_TASKBAR = "enable_taskbar";

    /**
     * Whether to show arrow keys in navigation bar
     * @hide
     */
    public static final String OMNI_NAVIGATION_BAR_ARROW_KEYS = "navigation_bar_menu_arrow_keys";

    /**
     * @hide
     */
    public static final String OMNI_GESTURE_HANDLE_HIDE = "navbar_gesture_handle_hide";

    /**
     * just for triggering an update - DOES NOT need a backup
     * @hide
     */
    public static final String OMNI_CUSTOM_FP_ICON_UPDATE = "custom_fingerprint_icon_update";

    /**
     * @hide
     */
    public static final String OMNI_GESTURE_HANDLE_SMALL = "navbar_gesture_handle_small";

        /**
     * Whether the battery light should be enabled (if hardware supports it)
     * The value is boolean (1 or 0).
     * @hide
     */
    public static final String OMNI_BATTERY_LIGHT_ENABLED = "battery_light_enabled";

    /**
     * Whether to show battery light when DND mode is active
     * @hide
     */
    public static final String OMNI_BATTERY_LIGHT_ALLOW_ON_DND = "battery_light_allow_on_dnd";

    /**
     * Whether to show blinking light when battery is low
     * @hide
     */
    public static final String OMNI_BATTERY_LIGHT_LOW_BLINKING = "battery_light_low_blinking";

    /**
     * Low battery charging color
     * @hide
     */
    public static final String OMNI_BATTERY_LIGHT_LOW_COLOR = "battery_light_low_color";

    /**
     * Medium battery charging color
     * @hide
     */
    public static final String OMNI_BATTERY_LIGHT_MEDIUM_COLOR = "battery_light_medium_color";

    /**
    * Full battery charging color
    * @hide
    */
    public static final String OMNI_BATTERY_LIGHT_FULL_COLOR = "battery_light_full_color";

    /**
     * Really full 100 battery charging color
     * @hide
     */
    public static final String OMNI_BATTERY_LIGHT_REALLY_FULL_COLOR =
        "battery_light_really_full_color";

    /**
     * Whether the battery light should only be enabled on fully charged battery.
     * The value is boolean (1 or 0).
     * @hide
     */
    public static final String OMNI_BATTERY_LIGHT_ONLY_FULLY_CHARGED =
        "battery_light_only_fully_charged";

    /**
     * What color to use for the battery LED while charging - low
     * @hide
     */
    public static final String OMNI_FAST_BATTERY_LIGHT_COLOR = "fast_battery_light_color";

    /**
     * Whether the fast charging battery light is enabled
     * The value is boolean (1 or 0).
     * @hide
     */
    public static final String OMNI_FAST_CHARGING_LED_ENABLED = "fast_charging_led_enabled";

    /**
     * @hide
     */
    public static final String OMNI_USE_OLD_MOBILETYPE = "use_old_mobiletype";

    /**
     * @hide
     */
    public static final String OMNI_QS_LAYOUT_COLUMNS_LANDSCAPE = "qs_layout_columns_landscape";

    /**
     * @hide
     */
    public static final String OMNI_QS_LAYOUT_COLUMNS = "qs_layout_columns";

    /**
     * @hide
     */
    public static final String OMNI_QS_TILE_VERTICAL_LAYOUT = "qs_tile_vertical_layout";

    /**
     * @hide
     */
    public static final String OMNI_QS_TILE_LABEL_HIDE = "qs_tile_label_hide";

    /**
     * @hide
     */
    public static final String OMNI_LOW_BATTERY_BEHAVIOR = "battery_low_behavior";

    /**
     * @hide
     */
    public static final String OMNI_HIDE_ROAMING_ICON = "hide_roaming_icon";

    /**
     * SettingsBackupAgent will combine its list with this so we dont need
     * to add new things into SettingsProvider SystemSettings
     * @hide
     */
    public static final String[] OMNI_SETTINGS_TO_BACKUP = {
        // OMNI_FOO_BAR_BALABALA
        OMNI_SYSTEM_PROXI_CHECK_ENABLED,
        OMNI_DEVICE_PROXI_CHECK_ENABLED,
        OMNI_BUTTON_EXTRA_KEY_MAPPING,
        OMNI_DEVICE_FEATURE_SETTINGS,
        OMNI_LONG_PRESS_POWER_TORCH,
        OMNI_ADVANCED_REBOOT,
        OMNI_MONET_DISABLE,
        OMNI_SHOW_BATTERY_IMAGE,
        OMNI_LOCKSCREEN_WEATHER_ENABLED,
        OMNI_BACK_GESTURE_HEIGHT,
        OMNI_STATUS_BAR_ALARM,
        OMNI_ENABLE_TASKBAR,
        OMNI_NAVIGATION_BAR_ARROW_KEYS,
        OMNI_GESTURE_HANDLE_HIDE,
        OMNI_GESTURE_HANDLE_SMALL,
        OMNI_BATTERY_LIGHT_ENABLED,
        OMNI_BATTERY_LIGHT_ALLOW_ON_DND,
        OMNI_BATTERY_LIGHT_LOW_BLINKING,
        OMNI_BATTERY_LIGHT_LOW_COLOR,
        OMNI_BATTERY_LIGHT_MEDIUM_COLOR,
        OMNI_BATTERY_LIGHT_FULL_COLOR,
        OMNI_BATTERY_LIGHT_REALLY_FULL_COLOR,
        OMNI_BATTERY_LIGHT_ONLY_FULLY_CHARGED,
        OMNI_USE_OLD_MOBILETYPE,
        OMNI_QS_LAYOUT_COLUMNS_LANDSCAPE,
        OMNI_QS_LAYOUT_COLUMNS,
        OMNI_QS_TILE_VERTICAL_LAYOUT,
        OMNI_QS_TILE_LABEL_HIDE,
        OMNI_LOW_BATTERY_BEHAVIOR,
        OMNI_HIDE_ROAMING_ICON,
    };

    /**
     * SettingsBackupAgent will combine its list with this so we dont need
     * to add new things into SettingsProvider SystemSettingsValidators
     * we cant use Validators interface so use a simple integer mapping
     * BOOLEAN_VALIDATOR == 0
     * ANY_INTEGER_VALIDATOR == 1
     * ANY_STRING_VALIDATOR == 2
     * @hide
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
        OMNI_SETTINGS_VALIDATORS.put(OMNI_MONET_DISABLE, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_SHOW_BATTERY_IMAGE, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_LOCKSCREEN_WEATHER_ENABLED, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BACK_GESTURE_HEIGHT, 1);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_STATUS_BAR_ALARM, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_ENABLE_TASKBAR, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_NAVIGATION_BAR_ARROW_KEYS, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_GESTURE_HANDLE_HIDE, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_GESTURE_HANDLE_SMALL, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BATTERY_LIGHT_ENABLED, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BATTERY_LIGHT_ALLOW_ON_DND, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BATTERY_LIGHT_LOW_BLINKING, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BATTERY_LIGHT_LOW_COLOR, 1);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BATTERY_LIGHT_MEDIUM_COLOR, 1);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BATTERY_LIGHT_FULL_COLOR, 1);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BATTERY_LIGHT_REALLY_FULL_COLOR, 1);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_FAST_BATTERY_LIGHT_COLOR, 1);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_FAST_CHARGING_LED_ENABLED, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_BATTERY_LIGHT_ONLY_FULLY_CHARGED, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_USE_OLD_MOBILETYPE, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_QS_LAYOUT_COLUMNS_LANDSCAPE, 1);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_QS_LAYOUT_COLUMNS, 1);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_QS_TILE_VERTICAL_LAYOUT, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_QS_TILE_LABEL_HIDE, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_LOW_BATTERY_BEHAVIOR, 0);
        OMNI_SETTINGS_VALIDATORS.put(OMNI_HIDE_ROAMING_ICON, 0);
    }
}
