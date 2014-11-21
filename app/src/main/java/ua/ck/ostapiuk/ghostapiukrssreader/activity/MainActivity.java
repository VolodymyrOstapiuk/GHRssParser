package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
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
        if (getIntent().getExtras() != null) {
            mPost = (Post) getIntent().getExtras().getSerializable(Constants.POST_ID);
            onPostSelected(mPost);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            ((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list_fragment))
                    .refresh();
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
