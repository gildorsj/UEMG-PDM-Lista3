package com.example.lab3_ex3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        Button btnTocar = findViewById(R.id.btnTocar);
        Button btnParar = findViewById(R.id.btnParar);

        // Solicita permissão de notificação (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        // Botão TOCAR: inicia o MusicService
        btnTocar.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(this, MusicService.class);
            ContextCompat.startForegroundService(this, serviceIntent);
            tvStatus.setText("🎵 Tocando música...");
            Toast.makeText(this, "Música iniciada!", Toast.LENGTH_SHORT).show();
        });

        // Botão PARAR: encerra o MusicService
        btnParar.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(this, MusicService.class);
            stopService(serviceIntent);
            tvStatus.setText("Parado");
            Toast.makeText(this, "Música parada!", Toast.LENGTH_SHORT).show();
        });
    }
}
