package ua.ck.ostapiuk.ghostapiukrssreader.tasks;

import android.os.AsyncTask;
import android.view.View;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;
import ua.ck.ostapiuk.ghostapiukrssreader.repository.EntryRepository;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostListFragment;

/**
 * Created by Vova on 13.12.2014.
 */
public class GetFromDatabaseEntriesTask extends AsyncTask<Void, Void, List<Entry>> {
    public GetFromDatabaseEntriesTask(PostListFragment mFragment) {
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
    protected List<Entry> doInBackground(Void... voids) {
        try {
            return EntryRepository.getAllEntries(mFragment.getActivity());
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

}
