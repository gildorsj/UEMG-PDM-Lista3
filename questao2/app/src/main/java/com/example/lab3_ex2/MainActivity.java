package com.example.lab3_ex2;

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
        Button btnIniciar = findViewById(R.id.btnIniciar);
        Button btnParar = findViewById(R.id.btnParar);

        // Solicita permissão de notificação (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        // Botão INICIAR o serviço
        btnIniciar.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(this, MeuForegroundService.class);
            ContextCompat.startForegroundService(this, serviceIntent);
            tvStatus.setText("Serviço RODANDO");
            Toast.makeText(this, "Serviço iniciado!", Toast.LENGTH_SHORT).show();
        });

        // Botão PARAR o serviço
        btnParar.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(this, MeuForegroundService.class);
            stopService(serviceIntent);
            tvStatus.setText("Serviço PARADO");
            Toast.makeText(this, "Serviço parado!", Toast.LENGTH_SHORT).show();
        });
    }
}
