package ua.ck.ostapiuk.ghostapiukrssreader.model;

/**
 * Created by Vova on 07.11.2014.
 */
public class Post
{

    private String title;
    private String description;
    private String author;
    private String createdAt;
    private String url;

    public Post(String title, String description, String author, String createdAt, String url) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.createdAt = createdAt;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
