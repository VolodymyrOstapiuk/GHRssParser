package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import ua.ck.ostapiuk.ghostapiukrssreader.R;

public abstract class BaseFragment extends Fragment {
    protected void showContent(View view) {
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_indicator);
        View content = view.findViewById(R.id.content);
        View emptyView = view.findViewById(R.id.empty_view);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (content != null) {
            content.setVisibility(View.VISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    protected void showProgressBar(View view) {
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_indicator);
        View content = view.findViewById(R.id.content);
        View emptyView = view.findViewById(R.id.empty_view);
        if (content != null) {
            content.setVisibility(View.GONE);
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }

    }

    protected void showEmptyView(View view) {
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_indicator);
        View content = view.findViewById(R.id.content);
        View emptyView = view.findViewById(R.id.empty_view);
        if (content != null) {
            content.setVisibility(View.GONE);
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }

    }
}
