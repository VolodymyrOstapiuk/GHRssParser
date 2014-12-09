package ua.ck.ostapiuk.ghostapiukrssreader.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.activity.MainActivity;
import ua.ck.ostapiuk.ghostapiukrssreader.activity.PostViewerActivity;
import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.HabraHabrRssParser;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.RssParser;

public class NewPostsCheckerService extends Service {
    private List<Post> mPosts;
    private RssParser mParser;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_action_refresh)
                .setContentTitle("Сервис начал работу")
                .setContentText("Сервис по проверке нових записей начал роботу");
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0, resultIntent,
                        0
                );

        builder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        startForeground(Constants.NOTIFICATION_ID, notification);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new Handler();
        mParser = new HabraHabrRssParser();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    HabraHabrRssParser mParser = new HabraHabrRssParser();
                    List<Post> currentPosts = new ArrayList<Post>();
                    try {
                        currentPosts = mParser.getPosts(25);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    Post post = currentPosts.get(0);
                    if (mPosts != null) {
                        if (!(post.getTitle().equals(mPosts.get(0).getTitle()))) {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(NewPostsCheckerService.this)
                                    .setSmallIcon(R.drawable.ic_action_refresh)
                                    .setContentTitle("Новая запись:")
                                    .setContentText(post.getTitle());
                            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                            resultIntent.setAction(Constants.BROADCAST_FILTER);
                            resultIntent.putExtra(Constants.POST_ID, post);
                            PendingIntent resultPendingIntent =
                                    PendingIntent.getActivity(
                                            NewPostsCheckerService.this,
                                            0, resultIntent,
                                            0
                                    );

                            builder.setContentIntent(resultPendingIntent);
                            NotificationManager manager = (NotificationManager)
                                    getSystemService(NOTIFICATION_SERVICE);
                            Notification notification = builder.build();
                            manager.notify(1, notification);
                            sendBroadcast(resultIntent);
                        }
                    }
                    mPosts = currentPosts;
                    try {
                        TimeUnit.SECONDS.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent(Constants.BROADCAST_FILTER).putExtra(Constants.STATUS_ID, Constants.STATUS_STOPPED));
    }
}
