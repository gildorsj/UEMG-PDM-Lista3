package com.example.lab3_ex1;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothReceiver extends BroadcastReceiver {

    private OnStatusChangeListener listener;

    public interface OnStatusChangeListener {
        void onBluetoothChanged(String status);
    }

    public BluetoothReceiver(OnStatusChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

            switch (state) {
                case BluetoothAdapter.STATE_ON:
                    listener.onBluetoothChanged("Bluetooth ATIVADO");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    listener.onBluetoothChanged("Bluetooth DESATIVADO");
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    listener.onBluetoothChanged("Bluetooth ativando...");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    listener.onBluetoothChanged("Bluetooth desativando...");
                    break;
            }
        }
    }
}
