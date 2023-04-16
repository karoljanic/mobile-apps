package com.example.calendar

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderReceiver : BroadcastReceiver() {
    private val NOTIFICATION_CHANNEL = "calendarapp"
    private val NOTIFICATION_IDENTIFIER = 8787

    override fun onReceive(context: Context?, intent: Intent?) {
        val newIntent: Intent = Intent(context, MainActivity::class.java)
        newIntent.flags += Intent.FLAG_ACTIVITY_NEW_TASK
        newIntent.flags += Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0)

        val notificationCompatBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context!!, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.icon_notification_active_24)
                .setContentTitle(intent?.getStringExtra("title"))
                .setContentText(intent?.getStringExtra("content"))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

        val notificationManagerCompat: NotificationManagerCompat =
            NotificationManagerCompat.from(context)

        notificationManagerCompat.notify(NOTIFICATION_IDENTIFIER, notificationCompatBuilder.build())
    }
}