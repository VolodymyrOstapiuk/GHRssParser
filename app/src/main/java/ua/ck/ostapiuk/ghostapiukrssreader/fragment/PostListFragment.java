package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.adapter.PostAdapter;
import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.HabraHabrRssParser;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.RssParser;

/**
 * Created by Vova on 08.11.2014.
 */
public class PostListFragment extends BaseFragment {
    private OnPostSelectedListener listener;
    private PostAdapter adapter;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private List<Post> mPosts;
    public PostListFragment() {
        // Required empty public constructor
    }

    public interface OnPostSelectedListener {
        public void onPostSelected(Post post);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.post_list_fragment, container, false);
        mListView = (ListView)view.findViewById(R.id.content);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progress_indicator);
        return  view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnPostSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement the OnPostSelectedListener interface");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void refresh(Bundle args) {
        (new DownloadPostsTask()).execute(new HabraHabrRssParser());
    }
    private class DownloadPostsTask extends AsyncTask<RssParser,Void,List<Post>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mProgressBar!=null){
                mProgressBar.setVisibility(View.VISIBLE);
            }
            if (mListView!=null){
                mListView.setVisibility(View.GONE);
            }
        }

        @Override
        protected List<Post> doInBackground(RssParser... rssParsers) {
            try {
                return rssParsers[0].getAllPosts();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            if (posts==null)
            {
                throw new NullPointerException("post empty");
            }
            adapter =new PostAdapter(getActivity(),posts);
            if(mProgressBar!=null){
                mProgressBar.setVisibility(View.GONE);
            }
            if (mListView!=null){
                mListView.setVisibility(View.VISIBLE);
            }
            mPosts = posts;
            if (adapter!=null){
            mListView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onPostSelected(mPosts.get(i));
            }
        });
        (new DownloadPostsTask()).execute(new HabraHabrRssParser());


    }
}