package ua.ck.ostapiuk.ghostapiukrssreader.util.parser;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;
import ua.ck.ostapiuk.ghostapiukrssreader.util.container.ResponseContainer;

public class RssParser {
    public List<Entry> getEntries(String rssStandardURL) throws Exception {
        Gson gson = new Gson();
        ResponseContainer container = gson.fromJson(getJsonFromUrl(rssStandardURL),
                ResponseContainer.class);
        ResponseContainer.ResponseData responseData = container.getResponseData();
        ResponseContainer.Feed feed = responseData.getFeed();
        return feed.getEntries();
    }

    protected String getJsonFromUrl(String url) throws IOException, JSONException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String json = EntityUtils.toString(httpResponse.getEntity());
        return json;
    }
}
