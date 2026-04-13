package com.example.lab3_ex3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

public class MusicService extends Service {

    private static final String TAG = "MusicService";
    private static final String CHANNEL_ID = "music_service_channel";
    private static final int NOTIFICATION_ID = 1;

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        criarCanalDeNotificacao();
        Log.d(TAG, "onCreate - Serviço criado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand - Iniciando reprodução");

        // Cria a notificação obrigatória com título "Tocando música"
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Tocando música")
                .setContentText("Reproduzindo áudio em segundo plano...")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build();

        // Inicia o serviço em foreground
        startForeground(NOTIFICATION_ID, notification);

        // Inicializa o MediaPlayer com o arquivo da pasta res/raw
        // O arquivo deve se chamar "musica.mp3" dentro de res/raw/
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.musica);

            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true); // Repete continuamente
                mediaPlayer.start();
                Log.d(TAG, "MediaPlayer iniciado");
            }
        }

        // START_STICKY: garante que o serviço continue mesmo se o sistema tentar encerrá-lo
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy - Liberando MediaPlayer");

        // Libera o MediaPlayer corretamente
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }

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
                "Canal do Player de Música",
                NotificationManager.IMPORTANCE_LOW
        );
        channel.setDescription("Notificação de reprodução de música em segundo plano");

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }
}
