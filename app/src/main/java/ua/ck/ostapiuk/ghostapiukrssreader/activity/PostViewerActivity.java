package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.database.DaoSession;
import ua.ck.ostapiuk.ghostapiukrssreader.database.EntryDao;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.LoginFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostViewerFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.UserInformationFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.repository.EntryRepository;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

public class PostViewerActivity extends BaseActivity {
    private Entry mEntry;
    private Menu menu;
    private DaoSession daoSession;
    private boolean isEntryFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_view_activity);
        mEntry = (Entry) getIntent().getExtras().getSerializable(Constants.POST_ID);
        PostViewerFragment postViewerFragment = (PostViewerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.post_fragment);
        postViewerFragment.refresh(mEntry);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startActivity((new Intent(this, MainActivity.class).putExtra(Constants.POST_ID, mEntry)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_viewer, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.favorite_add) {
            if (mEntry!=null) {
                if (!isEntryFavorite(mEntry)) {
                    EntryRepository.insertOrUpdate(this, mEntry);
                    isEntryFavorite = true;
                    invalidateOptionsMenu();

                } else {
                    EntryRepository.getEntryDao(this).queryBuilder()
                        .where(EntryDao.Properties.Title.eq(mEntry.getTitle()))
                        .buildDelete().executeDeleteWithoutDetachingEntities();
                    isEntryFavorite = false;
                    invalidateOptionsMenu();

                }
            }
        } else {
            UserInformationFragment userInformationFragment = new UserInformationFragment();
            userInformationFragment.show(getSupportFragmentManager(), "Login");
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean isEntryFavorite(Entry entry){
        boolean isEntryFavourite;
        List<Entry> entries = EntryRepository.getEntryDao(this).queryBuilder()
            .where(EntryDao.Properties.Title.eq(entry.getTitle()))
            .list();
        return entries.size() > 0;
       }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.favorite_add);
        if (isEntryFavorite(mEntry)){
            item.setIcon(R.drawable.ic_action_toggle_star);
        }
        else {
            item.setIcon(R.drawable.ic_action_toggle_star_outline);
        }
        return true;
    }
}
