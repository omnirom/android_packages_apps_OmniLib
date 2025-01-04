/*
 * SPDX-FileCopyrightText: 2016 The CyanogenMod Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.omnirom.omnilib.internal.common;

import android.content.Context;
import org.omnirom.omnilib.internal.OmniRomSystemService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Helper methods for fetching a OmniRomSystemService from a class declaration
 */
public class OmniRomSystemServiceHelper {
    private Context mContext;

    public OmniRomSystemServiceHelper(Context context) {
        mContext = context;
    }

    public OmniRomSystemService getServiceFor(String className) {
        final Class<OmniRomSystemService> serviceClass;
        try {
            serviceClass = (Class<OmniRomSystemService>)Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Failed to create service " + className
                    + ": service class not found", ex);
        }

        return getServiceFromClass(serviceClass);
    }

    public <T extends OmniRomSystemService> T getServiceFromClass(Class<T> serviceClass) {
        final T service;
        try {
            Constructor<T> constructor = serviceClass.getConstructor(Context.class);
            service = constructor.newInstance(mContext);
        } catch (InstantiationException ex) {
            throw new RuntimeException("Failed to create service " + serviceClass
                    + ": service could not be instantiated", ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Failed to create service " + serviceClass
                    + ": service must have a public constructor with a Context argument", ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Failed to create service " + serviceClass
                    + ": service must have a public constructor with a Context argument", ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Failed to create service " + serviceClass
                    + ": service constructor threw an exception", ex);
        }
        return service;
    }
}
