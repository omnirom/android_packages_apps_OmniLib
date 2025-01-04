/*
 * SPDX-FileCopyrightText: 2016 The CyanogenMod Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.omnirom.omnilib.internal;

import android.content.Context;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.SystemServiceManager;

import org.omnirom.omnilib.internal.common.OmniRomSystemServiceHelper;

/**
 * Base OmniRom System Server which handles the starting and states of various OmniRom
 * specific system services. Since its part of the main looper provided by the system
 * server, it will be available indefinitely (until all the things die).
 */
public class OmniRomSystemServer {
    private static final String TAG = "OmniRomSystemServer";
    private Context mSystemContext;
    private OmniRomSystemServiceHelper mSystemServiceHelper;

    private static final String ENCRYPTING_STATE = "trigger_restart_min_framework";
    private static final String ENCRYPTED_STATE = "1";

    public OmniRomSystemServer(Context systemContext) {
        mSystemContext = systemContext;
        mSystemServiceHelper = new OmniRomSystemServiceHelper(mSystemContext);
    }

    public static boolean coreAppsOnly() {
        // Only run "core" apps+services if we're encrypting the device.
        final String cryptState = SystemProperties.get("vold.decrypt");
        final boolean isAlarmBoot = SystemProperties.getBoolean("ro.alarm_boot", false);
        return ENCRYPTING_STATE.equals(cryptState) ||
               ENCRYPTED_STATE.equals(cryptState) ||
               isAlarmBoot;
    }

    /**
     * Invoked via reflection by the SystemServer
     */
    private void run() {
        // Start services.
        try {
            startServices();
        } catch (Throwable ex) {
            Slog.e("System", "******************************************");
            Slog.e("System", "************ Failure starting omnirom system services", ex);
            throw ex;
        }
    }

    private void startServices() {
        final Context context = mSystemContext;
        final SystemServiceManager ssm = LocalServices.getService(SystemServiceManager.class);
        String[] externalServices = context.getResources().getStringArray(
                org.omnirom.omnilib.R.array.config_externalOmniRomServices);

        for (String service : externalServices) {
            try {
                Slog.i(TAG, "Attempting to start service " + service);
                OmniRomSystemService omniromSystemService =  mSystemServiceHelper.getServiceFor(service);
                if (context.getPackageManager().hasSystemFeature(
                        omniromSystemService.getFeatureDeclaration())) {
                    if (coreAppsOnly() && !omniromSystemService.isCoreService()) {
                        Slog.d(TAG, "Not starting " + service +
                                " - only parsing core apps");
                    } else {
                        Slog.i(TAG, "Starting service " + service);
                        ssm.startService(omniromSystemService.getClass());
                    }
                } else {
                    Slog.i(TAG, "Not starting service " + service +
                            " due to feature not declared on device");
                }
            } catch (Throwable e) {
                reportWtf("starting " + service , e);
            }
        }
    }

    private void reportWtf(String msg, Throwable e) {
        Slog.w(TAG, "***********************************************");
        Slog.wtf(TAG, "BOOT FAILURE " + msg, e);
    }
}
