package ua.ck.ostapiuk.ghostapiukrssreader.util.parser;

import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

public class HabraHabrRssParser extends RssParser {
    public final static String RSS_URL = Constants.JSON_CONVERTER_URL +
            "http://habrahabr.ru/rss/feed/posts/a7acf97d45fcf1c06242ce6e5fee20a8/&num=";

    @Override
    public List<Post> getPosts(Integer count) throws IOException, JSONException {
        List<Post> posts = new ArrayList<Post>();

        JSONObject jObj = getJsonFromUrl(RSS_URL + count.toString());
        JSONArray postsJson = jObj.getJSONObject("responseData").getJSONObject("feed")
                .getJSONArray("entries");
        for (int i = 0; i < postsJson.length(); i++) {
            JSONObject jsonObject = postsJson.getJSONObject(i);
            Post post = new Post(jsonObject.getString("title"), jsonObject.getString("content")
                    , jsonObject.getString("author"), jsonObject.getString("publishedDate"),
                    jsonObject.getString("link"));
            posts.add(post);
        }


        return posts;
    }

}

