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

/**
 * Created by Vova on 07.11.2014.
 */
public abstract class RssParser
{
    private static InputStream is = null;
    private static String json = "";
    private static JSONObject jObj = null;
    public abstract List<Post> getAllPosts() throws Exception;
    protected InputStream getStreamFromUrl(String urlString) throws IOException
    {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        return connection.getInputStream();
    }
    protected JSONObject getJsonFromUrl(String url) throws IOException,JSONException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        json = EntityUtils.toString(httpResponse.getEntity());
        return new JSONObject(json);
   }}
