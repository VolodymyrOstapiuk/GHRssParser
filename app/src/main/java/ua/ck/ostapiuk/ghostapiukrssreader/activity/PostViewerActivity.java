package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.content.Intent;
import android.os.Bundle;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostViewerFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

public class PostViewerActivity extends BaseActivity
{
    private Post mPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_view_activity);
        mPost = (Post) getIntent().getExtras().getSerializable(Constants.POST_ID);
        PostViewerFragment postViewerFragment = (PostViewerFragment) getFragmentManager()
                .findFragmentById(R.id.post_fragment);
        postViewerFragment.refresh(mPost);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startActivity((new Intent(this, MainActivity.class).putExtra(Constants.POST_ID, mPost)));
    }
}
