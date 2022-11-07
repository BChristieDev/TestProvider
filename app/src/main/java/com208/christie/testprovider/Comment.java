package com208.christie.testprovider;

public class Comment {
    Integer id, postId;
    String name, email, body;

    /*
     * Below are the required getter functions used in order to access the private variables in the Comment class.
     */
    public Integer getId() {
        return id;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    /*
     * Below are the required setter functions used in order to change the private variables in the Comment class.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "Comment { id = " + id + ", postId = " + postId + ", name = \"" + name + "\", email = \"" + email + "\", body = \"" + body + "\"}";
    }
}