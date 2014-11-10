package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.adapter.PostAdapter;
import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.HabraHabrJsonRssParser;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.HabraHabrRssParser;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.RssParser;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.VogellaRssParser;

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
        (new DownloadPostsTask()).execute();
    }
    private class DownloadPostsTask extends AsyncTask<Void,Void,List<Post>>
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
        protected List<Post> doInBackground(Void... voids) {
            InputStream is=null;
            String json = "";
            JSONObject jObj = null;
            JSONArray jsonArray = null;
            String RSS_URL = "https://ajax.googleapis.com/ajax/services/feed/load?v=2.0&q=http://habrahabr.ru/rss/feed/posts/a7acf97d45fcf1c06242ce6e5fee20a8/&num=25";
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(RSS_URL);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                json = EntityUtils.toString(httpResponse.getEntity());

            }
                catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Post> posts = new ArrayList<Post>();

            try {
                jObj = new JSONObject(json);
                JSONArray postsJson = jObj.getJSONObject("responseData").getJSONObject("feed").getJSONArray("entries");
                for(int i = 0;i<postsJson.length();i++ )
                {
                    JSONObject jsonObject = postsJson.getJSONObject(i);
                    Post post = new Post(jsonObject.getString("title"),jsonObject.getString("content"),jsonObject.getString("author"),jsonObject.getString("publishedDate"),jsonObject.getString("link"));
                    posts.add(post);
                }

            }catch (JSONException e){
                e.printStackTrace();
            }

            return posts;

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
        (new DownloadPostsTask()).execute();


    }
}