package apps.webscare.firbasecloudmessaging.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import apps.webscare.firbasecloudmessaging.Acitvities.MainActivity;
import apps.webscare.firbasecloudmessaging.R;

public class MyFirabaseMessagingService extends FirebaseMessagingService {

    public static int NOTIFICATION_ID = 1 ;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        generateNotification(remoteMessage.getNotification().getTitle() , remoteMessage.getNotification().getBody());
    }

    private void generateNotification(String title, String body) {
        Intent intent = new Intent(this , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_ONE_SHOT);
        Uri soundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher_background)
                .setSound(soundURI)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (NOTIFICATION_ID > 1073741824){
            NOTIFICATION_ID = 0;
            Toast.makeText(this, "Message Out Of Range", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(this, "Message : " + body, Toast.LENGTH_SHORT).show();
            Log.d("TITLE", title);
            Log.d("BODY",body);
            notificationManager.notify(NOTIFICATION_ID++ , notificationBuilder.build());
        }
    }
}
