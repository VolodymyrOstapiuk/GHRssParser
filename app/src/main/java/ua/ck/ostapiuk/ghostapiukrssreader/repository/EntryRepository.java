package ua.ck.ostapiuk.ghostapiukrssreader.repository;

import android.content.Context;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.applicatioon.GHOstapiukRssReaderApplication;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;
import ua.ck.ostapiuk.ghostapiukrssreader.database.EntryDao;

public class EntryRepository {
    public static void insertOrUpdate(Context context, Entry entry) {
        getEntryDao(context).insertOrReplace(entry);
    }

    public static void clearEntries(Context context) {
        getEntryDao(context).deleteAll();
    }

    public static void deleteEntryWithId(Context context, long id) {
        getEntryDao(context).delete(getEntryById(context, id));
    }

    public static List<Entry> getAllEntries(Context context) {
        return getEntryDao(context).loadAll();
    }

    public static EntryDao getEntryDao(Context context) {
        return ((GHOstapiukRssReaderApplication) context.getApplicationContext())
                .getDaoSession().getEntryDao();
    }

    public static Entry getEntryById(Context context, long id) {
        return getEntryDao(context).load(id);
    }

}