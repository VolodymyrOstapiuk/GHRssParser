package ua.ck.ostapiuk.ghostapiukrssreader.dialogs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.google.android.gms.plus.PlusShare;

import java.util.Collections;
import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.adapter.ShareAdapter;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;

/**
 * Created by Vova on 09.01.2015.
 */
public class ShareDialog extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView mListView;
    private ShareAdapter mAdapter;
    private Entry mEntry;
    private UiLifecycleHelper helper;

    public ShareDialog(Entry mEntry) {
        this.mEntry = mEntry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.share_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.share_list);
        Context context = getActivity();
        view.findViewById(R.id.cancel).setOnClickListener(this);
        PackageManager manager = context.getPackageManager();
        Intent queryIntent = new Intent(Intent.ACTION_SEND);
        queryIntent.setType("text/plain");
        List<ResolveInfo> resolveInfos =
                manager.queryIntentActivities(queryIntent, 0);
        Collections.sort(resolveInfos,
                new ResolveInfo.DisplayNameComparator(manager));
        mAdapter = new ShareAdapter(getActivity(), resolveInfos, manager);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new UiLifecycleHelper(getActivity(), null);
        helper.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        helper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {

            }

            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        helper.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        helper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        helper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if ("com.facebook.katana".equals(mAdapter.getItem(i)
                .activityInfo.applicationInfo.packageName)) {
            if (FacebookDialog.canPresentShareDialog(getActivity(),
                    FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
                FacebookDialog dialog = new
                        FacebookDialog.ShareDialogBuilder(getActivity())
                        .setApplicationName(getActivity().getResources().getString(R.string.app_name))
                        .setName(mEntry.getTitle())
                        .setLink(mEntry.getLink())
                        .setDescription(mEntry.getDescription())
                        .build();
                helper.trackPendingDialogCall(dialog.present());


            }
        }
        if ("com.google.android.apps.plus".equals(mAdapter.getItem(i).
                activityInfo.applicationInfo.packageName)) {
            Intent shareIntent = new PlusShare.Builder(getActivity())
                    .setType("text/plain")
                    .setText(mEntry.getDescription())
                    .getIntent();
            startActivityForResult(shareIntent, 0);
        }

    }
}

