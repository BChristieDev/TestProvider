package com208.christie.testprovider;

public class Post {
    Integer id, userId;
    String title, body;

    /*
     * Below are the required getter functions used in order to access the private variables in the Post class.
     */
    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    /*
     * Below are the required setter functions used in order to change the private variables in the Post class.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /*
     * toString: toString is an overwritten function of type String that will display all of the data in the specific
     * instance of the Album class. This is the data that will be used to populate the ListView.
     */
    @Override
    public String toString() {
        return "Post { id = " + id + ", userId = " + userId + ", title = \"" + title + "\", body = \"" + body + "\"}";
    }
}