package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.os.Bundle;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostViewerFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

public class PostViewerActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_view_activity);
        PostViewerFragment postViewerFragment = (PostViewerFragment) getFragmentManager()
                .findFragmentById(R.id.post_fragment);
        postViewerFragment.refresh((Post) getIntent().getExtras().getSerializable(Constants.POST_ID));
    }
}
