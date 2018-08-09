/*
 *  Copyright (C) 2016 The OmniROM Project
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
package org.omnirom.omnilib.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.omnirom.omnilib.R;

import com.android.settingslib.CustomDialogPreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.omnirom.omnilib.R;

import com.android.settingslib.CustomDialogPreference;

public class BTPairedDevicesListPreference extends CustomDialogPreference {
    private List<String> mValues = new ArrayList<String>();
    private final List<DeviceItem> mDeviceItemList = new ArrayList<DeviceItem>();

    public BTPairedDevicesListPreference(Context context) {
        this(context, null);
    }

    public BTPairedDevicesListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.preference_bt_devices_list);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice bt : pairedDevices) {
            mDeviceItemList.add(new DeviceItem(bt.getName()));
        }
        Collections.sort(mDeviceItemList);

        setPositiveButtonText(R.string.action_save);
        setNegativeButtonText(android.R.string.cancel);
    }

    public void setValues(Collection<String> values) {
        mValues.clear();
        mValues.addAll(values);
    }

    public Collection<String> getValues() {
        return mValues;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mAdapter = new DeviceListAdapter(getContext());
        final ListView listView = (ListView) view.findViewById(R.id.device_list);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AppViewHolder holder = (AppViewHolder) view.getTag();
                final boolean isChecked = !holder.checkBox.isChecked();

                holder.checkBox.setChecked(isChecked);
                DeviceItem info = mAdapter.getItem(position);

                if (isChecked) {
                    mValues.add(info.mValue);
                } else {
                    mValues.remove(info.mValue);
                }
            }
        });
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            callChangeListener(mValues.size() > 0 ? mValues : null);
        }
    }

    public class DeviceItem implements Comparable<DeviceItem> {
        public final String mName;

        DeviceItem(String value) {
            mName = value;
        }

        @Override
        public int compareTo(DeviceItem another) {
            return mName.toUpperCase().compareTo(another.mName.toUpperCase());
        }

        @Override
        public int hashCode() {
            return mName.hashCode();
        }

        @Override
        public boolean equals(Object another) {
            if (another == null || !(another instanceof DeviceItem)) {
                return false;
            }
            return mName.equals(((DeviceItem) another).mName);
        }
    }


    public class DeviceListAdapter extends ArrayAdapter<DeviceItem> {
        private final LayoutInflater mInflater;

        public DeviceListAdapter(Context context) {
            super(context, 0);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            addAll(mDeviceItemList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppViewHolder holder = AppViewHolder.createOrRecycle(mInflater, convertView);
            convertView = holder.rootView;
            DeviceItem info = getItem(position);
            holder.deviceName.setText(info.mName);
            holder.checkBox.setChecked(mValues.contains(info.mName));
            return convertView;
        }

        @Override
        public DeviceItem getItem(int position) {
            return mDeviceItemList.get(position);
        }
    }

    public static class AppViewHolder {
        public View rootView;
        public TextView deviceName;
        public CheckBox checkBox;

        public static AppViewHolder createOrRecycle(LayoutInflater inflater, View convertView) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.bt_device_item, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                AppViewHolder holder = new AppViewHolder();
                holder.rootView = convertView;
                holder.deviceName = (TextView) convertView.findViewById(R.id.device_name);
                holder.checkBox = (CheckBox) convertView.findViewById(android.R.id.checkbox);
                convertView.setTag(holder);
                return holder;
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                return (AppViewHolder) convertView.getTag();
            }
        }
    }
}