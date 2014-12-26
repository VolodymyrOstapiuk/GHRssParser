package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostListFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostViewerFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.repository.EntryRepository;
import ua.ck.ostapiuk.ghostapiukrssreader.service.NewPostsCheckerService;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;


public class MainActivity extends BaseActivity implements PostListFragment.OnPostSelectedListener {
    private Entry mEntry;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mEntry = (Entry) intent.getSerializableExtra(Constants.POST_ID);
            ((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list_fragment))
                    .refresh(Constants.INTERNET_MODE);
            onPostSelected(mEntry);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(receiver, new IntentFilter(Constants.BROADCAST_FILTER));
        startService(new Intent(this, NewPostsCheckerService.class));
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getInt(Constants.MODE_ID) == Constants.DATABASE_MODE) {
                ((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list_fragment))
                        .refresh(Constants.DATABASE_MODE);
            }
            mEntry = (Entry) getIntent().getExtras().getSerializable(Constants.POST_ID);
            onPostSelected(mEntry);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isDualPane()) {
            getMenuInflater().inflate(R.menu.main_single_pane, menu);
        } else {
            getMenuInflater().inflate(R.menu.main_dual_pane, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (!isDualPane()) {
            switch (id) {
                case R.id.refresh:
                    ((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list_fragment))
                            .refresh(Constants.INTERNET_MODE);
                    break;
                case R.id.favorite_list:
                    ((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list_fragment))
                            .refresh(Constants.DATABASE_MODE);
                    break;
            }
        } else {
            switch (id) {
                case R.id.refresh:
                    ((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list_fragment))
                            .refresh(Constants.INTERNET_MODE);
                    break;
                case R.id.favorite_list:
                    ((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list_fragment))
                            .refresh(Constants.DATABASE_MODE);
                    break;
                case R.id.favorite_add:
                    EntryRepository.insertOrUpdate(this, mEntry);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostSelected(Entry entry) {
        mEntry = entry;
        if (!isDualPane()) {
            Intent intent = new Intent(this, PostViewerActivity.class);
            intent.putExtra(Constants.POST_ID, entry);
            startActivity(intent);

        } else {
            PostViewerFragment postViewerFragment = (PostViewerFragment) getFragmentManager()
                    .findFragmentById(R.id.post_fragment);
            postViewerFragment.refresh(entry);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.POST_ID, mEntry);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onPostSelected((Entry) savedInstanceState.getSerializable(Constants.POST_ID));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getInt(Constants.MODE_ID) == Constants.DATABASE_MODE) {
                ((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list_fragment))
                        .refresh(Constants.DATABASE_MODE);
            }
        }
    }
}

