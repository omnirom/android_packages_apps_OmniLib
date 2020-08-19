/*
 *  Copyright (C) 2020 The OmniROM Project
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

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.webkit.URLUtil;

public class OmniServiceLocator {

    private static String getWalllpaperBaseUrl(Context context) {
        String s = Settings.System.getString(context.getContentResolver(), "wallpaper_base_url");
        if (TextUtils.isEmpty(s)) {
            return "https://dl.omnirom.org/";
        }
        return s;
    }

    private static String getWalllpaperRootUri(Context context) {
        String s = Settings.System.getString(context.getContentResolver(), "wallpaper_root_uri");
        if (TextUtils.isEmpty(s)) {
            return "images/wallpapers/";
        }
        return s;
    }

    private static String getWalllpaperQueryUri(Context context) {
        String s = Settings.System.getString(context.getContentResolver(), "wallpaper_query_uri");
        if (TextUtils.isEmpty(s)) {
            return "images/wallpapers/thumbs/json_wallpapers_xml.php";
        }
        return s;
    }

    private static String getHeaderBaseUrl(Context context) {
        String s = Settings.System.getString(context.getContentResolver(), "header_base_url");
        if (TextUtils.isEmpty(s)) {
            return "https://dl.omnirom.org/";
        }
        return s;
    }

    private static String getHeaderRootUri(Context context) {
         String s = Settings.System.getString(context.getContentResolver(), "header_root_uri");
        if (TextUtils.isEmpty(s)) {
            return "images/headers/";
        }
        return s;
    }

    private static String getHeaderQueryUri(Context context) {
        String s = Settings.System.getString(context.getContentResolver(), "header_query_uri");
        if (TextUtils.isEmpty(s)) {
            return "images/headers/thumbs/json_headers_xml.php";
        }
        return s;
    }

    public static String getStoreBaseUrl(Context context) {
        return "https://dl.omnirom.org/";
    }

    public static String getStoreRootUri(Context context) {
        return "store";
    }

    public static String getStoreQuertUri(Context context) {
        return "store/apps.json";
    }

    public static String getBuildsBaseUrl(Context context) {
        return "https://dl.omnirom.org/";
    }

    public static String getBuildsRootUri(Context context) {
        return null;
    }

    public static String getBuildsQuertUri(Context context) {
        return "json.php";
    }

    public static String getBuildsSecondaryBaseUrl(Context context) {
        return "https://dl.omnirom.org/";
    }

    public static String getBuildsSecondaryRootUri(Context context) {
        return "tmp";
    }

    public static String getBuildsSecondaryQuertUri(Context context) {
        return "tmp/json.php";
    }

    public static String getBuildsDeltaBaseUrl(Context context) {
        return "https://delta.omnirom.org/delta";
    }

    public static String getBuildsDeltaRootUri(Context context) {
        return "weeklies";
    }

    public static String buildWalllpaperQueryUrl(Context context) {
        String queryUri = getWalllpaperQueryUri(context);
        if (URLUtil.isNetworkUrl(queryUri)) {
            return queryUri;
        }
        Uri base = Uri.parse(getWalllpaperBaseUrl(context));
        Uri u = Uri.withAppendedPath(base, queryUri);
        return u.toString();
    }

    public static String buildWalllpaperRootUrl(Context context) {
        String rootUri = getWalllpaperRootUri(context);
        if (URLUtil.isNetworkUrl(rootUri)) {
            return rootUri;
        }
        Uri base = Uri.parse(getWalllpaperBaseUrl(context));
        Uri u = Uri.withAppendedPath(base, rootUri);
        return u.toString();
    }
    
    public static String buildHeaderQueryUrl(Context context) {
        String queryUri = getHeaderQueryUri(context);
        if (URLUtil.isNetworkUrl(queryUri)) {
            return queryUri;
        }
        Uri base = Uri.parse(getHeaderBaseUrl(context));
        Uri u = Uri.withAppendedPath(base, queryUri);
        return u.toString();
    }

    public static String buildHeaderRootUrl(Context context) {
        String rootUri = getHeaderRootUri(context);
        if (URLUtil.isNetworkUrl(rootUri)) {
            return rootUri;
        }
        Uri base = Uri.parse(getHeaderBaseUrl(context));
        Uri u = Uri.withAppendedPath(base, rootUri);
        return u.toString();
    }
}
