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
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.HabraHabrRssParser;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.RssParser;

public class PostListFragment extends BaseFragment {
    private OnPostSelectedListener mListener;
    private PostAdapter mAdapter;
    private ListView mListView;
    private List<Post> mPosts;
    private View mView;
    public PostListFragment() {
        // Required empty public constructor
    }

    public interface OnPostSelectedListener {
        public void onPostSelected(Post post);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView)view.findViewById(R.id.content);
        mView = view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPostSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException
                    ("Activity must implement the OnPostSelectedListener interface");
        }
    }

    public void refresh() {
        (new DownloadPostsTask()).execute(new HabraHabrRssParser());
    }
    private class DownloadPostsTask extends AsyncTask<RssParser,Void,List<Post>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar(mView);
        }

        @Override
        protected List<Post> doInBackground(RssParser... rssParsers) {
            try {
                return rssParsers[0].getPosts(Constants.STANDARD_POSTS_COUNT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            mPosts = posts;
            mAdapter = new PostAdapter(getActivity(), posts);
            mListView.setAdapter(mAdapter);
            showContent(mView);
            }
        }


    @Override
    public void onStart() {
        super.onStart();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onPostSelected(mPosts.get(i));
            }
        });
        (new DownloadPostsTask()).execute(new HabraHabrRssParser());


    }

}