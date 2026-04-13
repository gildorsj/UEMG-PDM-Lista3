package com.example.lab3_ex1;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BluetoothReceiver bluetoothReceiver;
    private AirplaneModeReceiver airplaneModeReceiver;

    private TextView tvBluetoothStatus;
    private TextView tvAirplaneStatus;
    private TextView tvLog;

    private StringBuilder logBuilder = new StringBuilder("Log de eventos:\n");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBluetoothStatus = findViewById(R.id.tvBluetoothStatus);
        tvAirplaneStatus = findViewById(R.id.tvAirplaneStatus);
        tvLog = findViewById(R.id.tvLog);

        // ------------------------------------------
        // Receiver 1: Bluetooth
        // ------------------------------------------
        bluetoothReceiver = new BluetoothReceiver(status -> {
            tvBluetoothStatus.setText(status);
            addLog(status);
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        });

        // ------------------------------------------
        // Receiver 2: Modo Avião
        // ------------------------------------------
        airplaneModeReceiver = new AirplaneModeReceiver(status -> {
            tvAirplaneStatus.setText(status);
            addLog(status);
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Registra o receiver do Bluetooth
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothReceiver, bluetoothFilter);

        // Registra o receiver do Modo Avião
        IntentFilter airplaneFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeReceiver, airplaneFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Desregistra os receivers para evitar memory leaks
        unregisterReceiver(bluetoothReceiver);
        unregisterReceiver(airplaneModeReceiver);
    }

    private void addLog(String mensagem) {
        logBuilder.append("• ").append(mensagem).append("\n");
        tvLog.setText(logBuilder.toString());
    }
}
