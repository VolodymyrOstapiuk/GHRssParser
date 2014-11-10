package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.adapter.PostAdapter;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostListFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostViewerFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;


public class MainActivity extends BaseActivity implements PostListFragment.OnPostSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostSelected(Post post) {
        if (!getResources().getBoolean(R.bool.isTablet)) {
            Intent intent = new Intent(this, PostViewerActivity.class);
            intent.putExtra(PostViewerFragment.POST_TITLE, post.getTitle());
            intent.putExtra(PostViewerFragment.POST_DESCRIPTION, post.getDescription());
            intent.putExtra(PostViewerFragment.POST_URL,post.getUrl());
            startActivity(intent);

        } else {
            Bundle args = new Bundle();
            args.putString(PostViewerFragment.POST_TITLE, post.getTitle());
            args.putString(PostViewerFragment.POST_DESCRIPTION, post.getDescription());
            args.putString(PostViewerFragment.POST_URL,post.getUrl());
            PostViewerFragment postViewerFragment = (PostViewerFragment) getFragmentManager().findFragmentById(R.id.post_fragment);
            postViewerFragment.refresh(args);
        }
    }
    }
