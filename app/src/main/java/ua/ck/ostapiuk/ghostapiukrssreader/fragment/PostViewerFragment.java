package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

public class PostViewerFragment extends BaseFragment
{
    private TextView mTitleView;
    private WebView mDescriptionView;
    private TextView mUrlView;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_fragment,container,false);
            }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleView =(TextView)view.findViewById(R.id.titleTextView);
        mDescriptionView = (WebView)view.findViewById(R.id.descriptionView);
        mUrlView = (TextView)view.findViewById(R.id.urlTextView);
        mView = view;
        mDescriptionView.setBackgroundColor(Constants.TRANSPARENT_COLOR);
    }

    public void refresh(Post post) {
        if (post == null) {
            showEmptyView(mView);
        } else {
            mTitleView.setText(post.getTitle());
            mDescriptionView.loadDataWithBaseURL(null, post.getDescription(), "text/html", "UTF-8", null);
            mUrlView.setText(post.getUrl());
        }

    }
    }
