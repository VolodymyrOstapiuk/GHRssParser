package ua.ck.ostapiuk.ghostapiukrssreader.util.parser;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.model.Post;

public abstract class RssParser {
    public abstract List<Post> getPosts(Integer count) throws Exception;

    protected JSONObject getJsonFromUrl(String url) throws IOException, JSONException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String json = EntityUtils.toString(httpResponse.getEntity());
        return new JSONObject(json);
    }
}
