package com.example.lab3_ex2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

public class MeuForegroundService extends Service {

    private static final String CHANNEL_ID = "foreground_service_channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        criarCanalDeNotificacao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Cria a notificação persistente
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Serviço Ativo")
                .setContentText("O serviço está rodando em primeiro plano.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build();

        // Inicia o serviço em foreground com a notificação
        startForeground(NOTIFICATION_ID, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Cria o canal de notificação (obrigatório para Android 8+).
     */
    private void criarCanalDeNotificacao() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Canal do Serviço Foreground",
                NotificationManager.IMPORTANCE_LOW
        );
        channel.setDescription("Notificações do serviço em primeiro plano");

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }
}
