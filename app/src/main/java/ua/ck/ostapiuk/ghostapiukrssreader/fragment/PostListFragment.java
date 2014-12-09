package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.adapter.PostAdapter;
import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.HabraHabrRssParser;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.RssParser;

public class PostListFragment extends BaseFragment {
    private OnPostSelectedListener mListener;
    private PostAdapter mAdapter;
    private ListView mListView;
    private List<Post> mPosts;
    private View mView;
    private final int NOTIFICATION_ID = 001;
    public PostListFragment() {
        // Required empty public constructor
    }

    /*private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getExtras().getInt(Constants.STATUS_ID);
            switch (status) {
                case Constants.STATUS_STARTED:
                    displayServiceStartedNotification();
                    break;
                case Constants.STATUS_RUN:
                     Post newPost = (Post)intent.getExtras().getSerializable(Constants.POST_ID);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                            .setContentTitle("Новая запись")
                            .setContentText(newPost.getTitle());
                    Intent resultIntent = new Intent(getActivity(),getActivity().getClass());
                    resultIntent.putExtra(Constants.POST_ID,newPost);
                    PendingIntent resultPendingIntent = PendingIntent.getActivity(getActivity(),0,resultIntent
                            ,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(resultPendingIntent);
                    NotificationManager manager = (NotificationManager)
                            getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(NOTIFICATION_ID,builder.build());
                    break;
            }
        }
    };*/
    public interface OnPostSelectedListener {
        public void onPostSelected(Post post);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
//        getActivity().registerReceiver(mReceiver,new IntentFilter(Constants.BROADCAST_FILTER));
        displayServiceStartedNotification();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView)view.findViewById(R.id.content);
        mView = view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPostSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException
                    ("Activity must implement the OnPostSelectedListener interface");
        }
    }

    public void refresh() {
        executeTaskWithConnectionChecking();
    }
    private class DownloadPostsTask extends AsyncTask<RssParser,Void,List<Post>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar(mView);
        }

        @Override
        protected List<Post> doInBackground(RssParser... rssParsers) {
            try {
                return rssParsers[0].getPosts(Constants.STANDARD_POSTS_COUNT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            mPosts = posts;
            mAdapter = new PostAdapter(getActivity(), posts);
            mListView.setAdapter(mAdapter);
            showContent(mView);
            }
        }


    @Override
    public void onStart() {
        super.onStart();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onPostSelected(mPosts.get(i));
            }
        });
        executeTaskWithConnectionChecking();
    }

    private boolean isConnectionAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void showWiFiSwitchOnDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Network");
        alertDialog.setMessage("Do you want to turn the WiFi?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    private void executeTaskWithConnectionChecking() {
        if (isConnectionAvailable()) {
            (new DownloadPostsTask()).execute(new HabraHabrRssParser());
        } else {
            showWiFiSwitchOnDialog();
        }
    }

    private void displayServiceStartedNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Сервис начал работу")
                .setContentText("Сервис по проверке нових записей начал роботу");
        Intent resultIntent = new Intent(getActivity(), getActivity().getClass());
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getActivity(), 0, resultIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager)
                getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());

    }
}