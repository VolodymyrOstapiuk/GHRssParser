package ua.ck.ostapiuk.ghostapiukrssreader;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import ua.ck.ostapiuk.ghostapiukrssreader.entity.DaoMaster;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.DaoSession;

/**
 * Created by Vova on 15.12.2014.
 */
public class GHOstapiukRssReaderApplication extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseSetup();
    }

    private void databaseSetup() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "entry-id", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
