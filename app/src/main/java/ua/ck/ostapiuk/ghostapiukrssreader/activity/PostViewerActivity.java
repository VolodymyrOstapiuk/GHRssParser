package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.database.DaoSession;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.LoginFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.fragment.PostViewerFragment;
import ua.ck.ostapiuk.ghostapiukrssreader.repository.EntryRepository;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

public class PostViewerActivity extends BaseActivity {
    private Entry mEntry;
    private Menu menu;
    private DaoSession daoSession;

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
            EntryRepository.insertOrUpdate(this, mEntry);
        } else {
            LoginFragment loginFragment = new LoginFragment();
            loginFragment.show(getSupportFragmentManager(), "Login");
        }
        return super.onOptionsItemSelected(item);
    }
}
