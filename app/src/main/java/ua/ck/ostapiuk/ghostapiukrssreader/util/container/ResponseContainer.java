package ua.ck.ostapiuk.ghostapiukrssreader.util.container;

import java.util.List;

import ua.ck.ostapiuk.ghostapiukrssreader.entity.Entry;

public class ResponseContainer {
    private ResponseData responseData;

    public static class ResponseData {
        private Feed feed;

        public void setFeed(Feed feed) {
            this.feed = feed;
        }

        public Feed getFeed() {
            return feed;
        }
    }

    public static class Feed {
        private String feedUrl;
        private String title;
        private String link;
        private String author;
        private String description;
        private String type;
        private List<Entry> entries;

        public String getFeedUrl() {
            return feedUrl;
        }

        public void setFeedUrl(String feedUrl) {
            this.feedUrl = feedUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Entry> getEntries() {
            return entries;
        }

        public void setEntries(List<Entry> entries) {
            this.entries = entries;
        }
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }
}
