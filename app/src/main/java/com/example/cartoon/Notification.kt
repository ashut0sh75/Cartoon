/*

package com.example.cartoon

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.shashank.sony.fancytoastlib.FancyToast

class Notification(private val context: Context) {

    private val CHANNEL_ID = "channelId"
    private val CHANNEL_NAME = "channelName"

    init {
        createNotificationChannel()
    }

    fun sendNotification(title: String, message: String, smallIcon: Int, notificationId: Int) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // You need to request the notification permission.
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission is already granted; you can send the notification.
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(smallIcon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, notification)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "This is my notification channel"
                lightColor = Color.GRAY
                enableLights(true)
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}

*/

package com.example.cartoon

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.shashank.sony.fancytoastlib.FancyToast

class Notification(private val context: Context) {

    private val CHANNEL_ID = "channelId"
    private val CHANNEL_NAME = "channelName"

    init {
        createNotificationChannel()
    }

    fun sendNotification(title: String, message: String, smallIcon: Int, notificationId: Int) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
             requestNotificationPermission()
        } else {
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(smallIcon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, notification)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "This is my notification channel"
                lightColor = Color.GRAY
                enableLights(true)
            }

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    public fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            PERMISSION_REQUEST_CODE
        )
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 128956
    }
}

