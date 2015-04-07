package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;


import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.adapter.EntryAdapter;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;
import ua.ck.ostapiuk.ghostapiukrssreader.tasks.GetFromDatabaseEntriesTask;
import ua.ck.ostapiuk.ghostapiukrssreader.tasks.DownloadEntriesTask;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

public class PostListFragment extends BaseFragment {
    private OnPostSelectedListener mListener;
    private ListView mListView;
    private List<Entry> mEntries;
    public PostListFragment() {
        // Required empty public constructor
    }
    public interface OnPostSelectedListener {
        public void onPostSelected(Entry entry);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    public void refresh(int mode) {
        switch (mode) {
            case Constants.INTERNET_MODE:
                (new DownloadEntriesTask(this)).executeWithConnectionChecking(Constants.HABRAHABR_RSS_URL);
                break;
            case Constants.DATABASE_MODE:
                (new GetFromDatabaseEntriesTask(this)).execute();
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onPostSelected(mEntries.get(i));
            }
        });
        (new DownloadEntriesTask(this)).executeWithConnectionChecking(Constants.HABRAHABR_RSS_URL);
    }

    public void setAdapterEntries(List<Entry> entries) {
        mEntries = entries;
        mListView.setAdapter(new EntryAdapter(getActivity(), entries));
    }
    public void selectFirstEntry(){
        mListener.onPostSelected(mEntries.get(0));
    }

}