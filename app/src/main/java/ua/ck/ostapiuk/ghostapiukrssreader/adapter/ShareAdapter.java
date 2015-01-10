package ua.ck.ostapiuk.ghostapiukrssreader.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.R;

/**
 * Created by Vova on 09.01.2015.
 */
public class ShareAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResolveInfo> mResolveInfoList;
    private PackageManager mPackageManager;
    private LayoutInflater mLayoutInflater;

    static class ViewHolder {
        public ImageView icon;
        public TextView name;
    }

    public ShareAdapter(Context mContext,
                        List<ResolveInfo> mResolveInfoList, PackageManager mPackageManager) {
        this.mContext = mContext;
        this.mResolveInfoList = mResolveInfoList;
        this.mPackageManager = mPackageManager;
        mLayoutInflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mResolveInfoList.size();
    }

    @Override
    public ResolveInfo getItem(int i) {
        return mResolveInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.share_list_item, viewGroup, false);
        }
        ResolveInfo resolveInfo = getItem(i);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
        viewHolder.name = (TextView) view.findViewById(R.id.name);
        view.setTag(viewHolder);
        viewHolder.icon.setImageDrawable(resolveInfo.loadIcon(mPackageManager));
        viewHolder.name.setText(resolveInfo.loadLabel(mPackageManager));
        return view;
    }
}
