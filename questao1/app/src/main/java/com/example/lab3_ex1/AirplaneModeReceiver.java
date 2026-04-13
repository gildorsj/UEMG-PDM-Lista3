package com.example.lab3_ex1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class AirplaneModeReceiver extends BroadcastReceiver {

    private OnStatusChangeListener listener;

    public interface OnStatusChangeListener {
        void onAirplaneModeChanged(String status);
    }

    public AirplaneModeReceiver(OnStatusChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(action)) {
            boolean isAirplaneMode = Settings.Global.getInt(
                    context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

            if (isAirplaneMode) {
                listener.onAirplaneModeChanged("Modo Avião ATIVADO");
            } else {
                listener.onAirplaneModeChanged("Modo Avião DESATIVADO");
            }
        }
    }
}
