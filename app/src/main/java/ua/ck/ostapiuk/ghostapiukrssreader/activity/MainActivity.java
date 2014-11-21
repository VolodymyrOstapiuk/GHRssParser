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
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;


public class MainActivity extends BaseActivity implements PostListFragment.OnPostSelectedListener {
    private Post mPost;
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
        mPost = post;
        if (!isDualPane()) {
            Intent intent = new Intent(this, PostViewerActivity.class);
            intent.putExtra(Constants.POST_ID, post);
            startActivity(intent);

        } else {
            PostViewerFragment postViewerFragment = (PostViewerFragment) getFragmentManager()
                    .findFragmentById(R.id.post_fragment);
            postViewerFragment.refresh(post);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.POST_ID, mPost);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onPostSelected((Post) savedInstanceState.getSerializable(Constants.POST_ID));
    }
}
