package com.example.ivan.olympusgames;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Fernando on 22/06/2017.
 */

public class Notifications {

    public Notifications(Context contexto, int id, String titulo, String descripcion) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(contexto)
                        .setSmallIcon(R.drawable.ares)
                        .setContentTitle(titulo)
                        .setContentText(descripcion);
        NotificationManager mNotificationManager =
                (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
