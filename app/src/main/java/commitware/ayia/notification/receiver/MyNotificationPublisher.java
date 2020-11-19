package commitware.ayia.notification.receiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import commitware.ayia.notification.GlobalNotificationBuilder;
import commitware.ayia.notification.R;
import commitware.ayia.notification.handlers.NotificationActivity;



public class MyNotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id" ;

    public static final String NOTIFICATION_CHANNEL_ID = "notification-channel-id" ;

    public static final String NOTIFICATION_CONTENT = "content" ;

    private static final String TAG = "NotificationPublisher";

    public void onReceive (Context context , Intent intent) {

        NotificationManagerCompat mNotificationManagerCompat = NotificationManagerCompat.from(context);

       // Notification notification = intent.getParcelableExtra(NOTIFICATION) ;

        String content = intent.getStringExtra(NOTIFICATION_CONTENT);

        String channelID = intent.getStringExtra(NOTIFICATION_CHANNEL_ID);

        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;

        Log.d(TAG, "notification ID: " + id);
        Log.d(TAG, "channel ID: " + channelID);

        Notification notification = createNotification(content,  context, channelID, id);


        assert notification != null;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            NotificationChannel channel = new NotificationChannel(
//                    NOTIFICATION_CHANNEL_ID,
//                    "NotificationDemo",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            mNotificationManagerCompat.createNotificationChannel(channel);
//        }

        mNotificationManagerCompat.notify(id, notification);

    }


    private Notification createNotification(String content, Context context, String notificationChannelId, int id) {

        Intent mainIntent = new Intent(context, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack.
        stackBuilder.addParentStack(NotificationActivity.class);
        // Adds the Intent to the top of the stack.
        stackBuilder.addNextIntent(mainIntent);
        // Gets a PendingIntent containing the entire back stack.

        mainIntent.putExtra(NOTIFICATION_ID, id);

        PendingIntent mainPendingIntent =
                PendingIntent.getActivity(
                        context,
                        id,
                        mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(context, notificationChannelId);

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder);

        notificationCompatBuilder

        .setContentTitle("Subscription expired")
        .setContentText(content)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentIntent(mainPendingIntent)
        .setAutoCancel( true );

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationCompatBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
//        }

        return notificationCompatBuilder.build() ;
    }

}