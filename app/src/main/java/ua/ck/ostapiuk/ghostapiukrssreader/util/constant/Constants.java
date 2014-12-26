package ua.ck.ostapiuk.ghostapiukrssreader.util.constant;

public class Constants {
    public static final String JSON_CONVERTER_URL = "https://ajax.googleapis.com/ajax/services/feed/load?v=2.0&q=";
    public static final Integer TRANSPARENT_COLOR = 0x00000000;
    public final static Integer STANDARD_POSTS_COUNT = 25;
    public final static String POST_ID = "POST";
    public final static int STATUS_STOPPED = 0;
    public final static String STATUS_ID = "STATUS_ID";
    public final static String BROADCAST_FILTER = "notification";
    public final static int NOTIFICATION_ID = 1;
    public final static String HABRAHABR_RSS_URL = JSON_CONVERTER_URL +
            "http://habrahabr.ru/rss/feed/posts/a7acf97d45fcf1c06242ce6e5fee20a8" + "&num="
            + STANDARD_POSTS_COUNT;
    public final static String BASH_RSS_URL = JSON_CONVERTER_URL +
            "http://bash.im/rss/";
    public final static int DATABASE_MODE = 002;
    public final static int INTERNET_MODE = 001;
    public final static String MODE_ID = "MODE";

}
