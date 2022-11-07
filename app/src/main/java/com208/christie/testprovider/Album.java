package com208.christie.testprovider;

/**
 * Album: Album is a class that contains all of the data that can be found at the /albums path in the JSONPlaceholder API.
 */
public class Album {
    Integer id, userId;
    String title;

    /*
     * Below are the required getter functions used in order to access the private variables in the Album class.
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

    /*
     * Below are the required setter functions used in order to change the private variables in the Album class.
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

    /*
     * toString: toString is an overwritten function of type String that will display all of the data in the specific
     * instance of the Album class. This is the data that will be used to populate the ListView.
     */
    @Override
    public String toString() {
        return "Album { id = " + id + ", userId = " + userId + ", title = \"" + title + "\"}";
    }
}