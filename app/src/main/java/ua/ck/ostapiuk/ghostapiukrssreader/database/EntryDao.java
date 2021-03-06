package ua.ck.ostapiuk.ghostapiukrssreader.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table ENTRY.
 */
public class EntryDao extends AbstractDao<Entry, Long> {

    public static final String TABLENAME = "ENTRY";

    /**
     * Properties of entity Entry.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
        public final static Property Author = new Property(3, String.class, "author", false, "AUTHOR");
        public final static Property PublishedDate = new Property(4, String.class, "publishedDate", false, "PUBLISHED_DATE");
        public final static Property Link = new Property(5, String.class, "link", false, "LINK");
    }

    ;


    public EntryDao(DaoConfig config) {
        super(config);
    }

    public EntryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'ENTRY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TITLE' TEXT UNIQUE ," + // 1: title
                "'DESCRIPTION' TEXT," + // 2: description
                "'AUTHOR' TEXT," + // 3: author
                "'PUBLISHED_DATE' TEXT," + // 4: publishedDate
                "'LINK' TEXT);"); // 5: link
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ENTRY'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, Entry entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }

        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(4, author);
        }

        String publishedDate = entity.getPublishedDate();
        if (publishedDate != null) {
            stmt.bindString(5, publishedDate);
        }

        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(6, link);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Entry readEntity(Cursor cursor, int offset) {
        Entry entity = new Entry( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // author
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // publishedDate
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // link
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, Entry entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAuthor(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPublishedDate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLink(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(Entry entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(Entry entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
