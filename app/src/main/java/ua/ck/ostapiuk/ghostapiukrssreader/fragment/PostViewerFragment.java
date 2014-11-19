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

public class PostViewerFragment extends BaseFragment
{
    public final static String POST_TITLE = "POST_TITLE";
    public final static String POST_DESCRIPTION = "POST_DESCRIPTION";
    public final static String POST_URL = "POST_URL";
    private String mTitle;
    private String mDescription;
    private String mUrl;
    private TextView mTitleView;
    private WebView mDescriptionView;
    private TextView mUrlView;

    @Override
    public void onStart() {
        super.onStart();
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            mDescriptionView.loadUrl(mUrl);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_fragment,container,false);
            }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleView =(TextView)view.findViewById(R.id.titleTextView);
        mDescriptionView = (WebView)view.findViewById(R.id.descriptionView);
        mUrlView = (TextView)view.findViewById(R.id.urlTextView);
        mDescriptionView.setBackgroundColor(0x00000000);
    }

    @Override
    public void refresh(Bundle args) {
        if (args!=null){
            mTitle = args.getString(POST_TITLE);
            mDescription = args.getString(POST_DESCRIPTION);
            mUrl = args.getString(POST_URL);

        }
        mTitleView.setText(mTitle);
        mDescriptionView.loadDataWithBaseURL(null, mDescription, "text/html", "UTF-8", null);
        mUrlView.setText(mUrl);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDescriptionView.saveState(outState);
    }

    }
