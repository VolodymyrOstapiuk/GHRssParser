package ua.ck.ostapiuk.ghostapiukrssreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;

public class EntryAdapter extends BaseAdapter {
    private List<Entry> mEntries;
    private LayoutInflater mLayoutInflater;

    static class ViewHolder {
        TextView title;
        TextView author;
        TextView date;
    }

    public EntryAdapter(Context context, List<Entry> entries) {
        mEntries = entries;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.list_item_template, viewGroup, false);
        }
        Entry entry = getItem(i);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.title = (TextView) view.findViewById(R.id.titleTextView);
        viewHolder.author = (TextView) view.findViewById(R.id.authorTextView);
        viewHolder.date = (TextView) view.findViewById(R.id.createdAtTextView);
        view.setTag(viewHolder);
        viewHolder.title.setText(entry.getTitle());
        viewHolder.author.setText(entry.getAuthor());
        viewHolder.date.setText(entry.getPublishedDate());
        return view;

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Entry getItem(int i) {
        return mEntries.get(i);
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

}
