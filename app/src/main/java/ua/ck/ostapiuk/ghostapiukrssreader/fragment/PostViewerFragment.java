package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.dialogs.ShareDialog;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

public class PostViewerFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTitleView;
    private WebView mDescriptionView;
    private TextView mUrlView;
    private View mView;
    private Entry mEntry;
    private Button mShareButton;
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
        mShareButton = (Button) view.findViewById(R.id.share);
        mShareButton.setOnClickListener(this);
    }

    public void refresh(Entry entry) {
        if (entry == null) {
            showEmptyView(mView);
        } else {
            mShareButton.setVisibility(View.VISIBLE);
            mEntry = entry;
            mTitleView.setText(entry.getTitle());
            mDescriptionView.loadDataWithBaseURL(null, entry.getDescription(), "text/html", "UTF-8", null);
            mUrlView.setText(entry.getLink());
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.share) {
            ShareDialog dialog = ShareDialog.newInstance(mEntry);
            dialog.show(getActivity().getSupportFragmentManager(), "Share");
        }
    }
}
