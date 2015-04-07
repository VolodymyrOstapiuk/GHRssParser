package ua.ck.ostapiuk.ghostapiukrssreader.tasks;

import android.os.AsyncTask;
import android.view.View;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.dialogs.SwitchOnWifiDialog;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;

import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostListFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.util.connection.ConnectionChecker;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.RssParser;

/**
 * Created by Vova on 13.12.2014.
 */
public class DownloadEntriesTask extends AsyncTask<String, Void, List<Entry>> {
    public DownloadEntriesTask(PostListFragment mFragment) {
        this.mFragment = mFragment;
        mView = mFragment.getView();
    }

    private PostListFragment mFragment;
    private View mView;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mFragment.showProgressBar(mView);
    }

    @Override
    protected List<Entry> doInBackground(String... rssUrls) {
        try {
            RssParser rssParser = new RssParser();
            return rssParser.getEntries(rssUrls[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Entry> entries) {
        super.onPostExecute(entries);
        mFragment.setAdapterEntries(entries);
        mFragment.showContent(mView);
    }

    public void executeWithConnectionChecking(String... params) {
        if ((new ConnectionChecker()).isConnectionAvailable(mFragment)) {
            this.execute(params);
        } else {
            (new SwitchOnWifiDialog()).show(mFragment.getFragmentManager(), "Switch on dialog");
        }
    }

}
