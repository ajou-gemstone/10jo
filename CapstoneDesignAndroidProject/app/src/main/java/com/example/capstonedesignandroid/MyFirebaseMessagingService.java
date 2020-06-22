package com.example.capstonedesignandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String username, leaderormember, grouptitle, groupId;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handledf
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//
//        }

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> timelist = new ArrayList<String>();

        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            username = remoteMessage.getData().get("username");
            leaderormember = remoteMessage.getData().get("leaderormember");
            grouptitle = remoteMessage.getData().get("grouptitle");
            groupId = remoteMessage.getData().get("groupId");

            //Todo: 여기서 알림에 스택을 쌓는다. title을 기준으로 알림 목적을 나눈다.
            //Todo: 알람 스택은 안드로이드 내부 저장소에 저장하는게 좋을 것 같다. (어차피 자신만 사용, 서버 통신 필요x)
            //Todo: sharedPreference에 날짜, 시간, 내용 등을 넣는다. 시시각 초기화 필수

            //Todo: 아래에서 알람 view를 만들고 보낸다. 알람 종류에 따라서 다른 method를 작성한다.

            list.add(remoteMessage.getData().get("body"));
            timelist.add(DefinedMethod.getCurrentDate2());

            ArrayList<String> beforelist = SharedPreference.getStringArrayPref(getApplicationContext(),"notilist");
            ArrayList<String> beforetimelist = SharedPreference.getStringArrayPref(getApplicationContext(),"notitimelist");

            for(String a : beforelist)
                list.add(a);
            for(String a : beforetimelist)
                timelist.add(a);

            SharedPreference.setStringArrayPref(getApplicationContext(),"notilist", list);
            SharedPreference.setStringArrayPref(getApplicationContext(),"notitimelist", timelist);
            Log.d("sadfasdf", remoteMessage.getData().get("title")+"  "+remoteMessage.getData().get("body"));

            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
//    private void scheduleJob() {
//        // [START dispatch_job]
//        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
//                .build();
//        WorkManager.getInstance().beginWith(work).enqueue();
//        // [END dispatch_job]
//    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void sendNotification(String messageTitle, String messageBody) {

        if(messageBody.contains("수락") || messageBody.contains("거절") || messageBody.contains("신청") ) {
            Intent intent = new Intent(this, ReadGroupActivity.class);
            intent.putExtra("groupId", groupId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            setMessage(messageTitle, messageBody, pendingIntent);
        }
        else if(messageBody.contains("메시지")){
            Intent intent = new Intent(this, ChattingActivity.class);
            intent.putExtra("leaderormember", Integer.parseInt(leaderormember));
            intent.putExtra("username", username);
            intent.putExtra("grouptitle", grouptitle);
            intent.putExtra("groupId", groupId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            setMessage(messageTitle, messageBody, pendingIntent);
        }
        else if(messageBody.contains("패널티")) {
            Intent intent = new Intent(this, MyProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            setMessage(messageTitle, messageBody, pendingIntent);
        }
        else {
            Intent intent = new Intent(this, LectureroomCheckActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            setMessage(messageTitle, messageBody, pendingIntent);
        }

    }

    private void setMessage(String messageTitle, String messageBody, PendingIntent pendingIntent){
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.app)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
