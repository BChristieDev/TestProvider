package com208.christie.testprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * TestContentProvider: TestProvider is a class that extends the ContentProvider abstract class. This class will used to provide
 * content to the MainActivity. For this specific assignment, this class will be providing data from the JSONPlaceholder API,
 * and will be passed the path to the specific data type the user wishes to have returned to the MainActivity, allowing it
 * to be displayed on the user interface.
 */
public class TestContentProvider extends ContentProvider {

    public static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    final String TAG = "--++";
    Album[] albums;
    Post[] posts;
    Comment[] comments;

    public TestContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    /**
     * This method typically runs a database query, or makes a request to a remote API such as the JSONPlaceholder API.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        MATCHER.addURI(Contract.AUTHORITY, "albums", 1);
        MATCHER.addURI(Contract.AUTHORITY, "posts", 2);
        MATCHER.addURI(Contract.AUTHORITY, "comments", 3);

        String data;

        /*
         * url: url is a variable of type URL and will hold the URL for the path in which we are receiving JSON data from.
         */
        URL url;

        /*
         * connection: connection is a variable of type HttpURLConnection and will be responsible for sending an HTTP request to the specified URL.
         */
        HttpURLConnection connection;

        /*
         * inputStream: inputStream is a variable of type InputStream and will contain a byte stream received from specified URL.
         */
        InputStream inputStream;

        /*
         * inputStreamReader: inputStreamReader is a variable of type InputStreamReader and will convert the byte stream received from the specified URL into characters.
         */
        InputStreamReader inputStreamReader;

        /*
         * bufferedReader: bufferedReader is a variable of type BufferedReader and will read through the characters specified by the InputStreamReader.
         */
        BufferedReader bufferedReader;

        /*
         * stringBuilder: stringBuilder is a variable of type StringBuilder and will modify a string in place and is more efficient then concatenation.
         */
        StringBuilder stringBuilder;

        /*
         * line: line is a variable of type String and will contain one line of data specified from the StringBuilder.
         * json: json is a variable of type String and will contain every line of data specified from the StringBuilder.
         */
        String line, json;

        /*
         * gson: gson is a variable of type Gson and will be used to reformat all of the data in json into the Album class.
         */
        Gson gson;

        /*
         * First column needs to be "_id"; otherwise things can break;
         */
        String[] albumColumnNames = {"_id", "userId", "title"};
        String[] postColumnNames = {"_id", "userId", "title", "body"};
        String[] commentColumnNames = {"_id", "postId", "name", "email", "body"};

        MatrixCursor mc = null;
        MatrixCursor.RowBuilder rb;

        Log.i(TAG, "query: inside content provider");

        switch (MATCHER.match(uri)) {
            case 1:
                data = "albums";
                break;
            case 2:
                data = "posts";
                break;
            case 3:
                data = "comments";
                break;
            default:
                data = null;
        }

        try {
            String urlSpec = "https://jsonplaceholder.typicode.com/" + data;
            url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();

            Log.i(TAG, "getAlbums: connection made");

            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            json = stringBuilder.toString();

            Log.i(TAG, json);

            gson = new Gson();

            if (data == "albums") {
                mc = new MatrixCursor(albumColumnNames);
                albums = gson.fromJson(json, Album[].class);

                for (Album album : albums) {
                    Log.i(TAG, album.toString());

                    rb = mc.newRow();

                    rb.add("_id", album.getId());
                    rb.add("userId", album.getUserId());
                    rb.add("title", album.getTitle());
                }
            } else if (data == "posts") {
                mc = new MatrixCursor(postColumnNames);
                posts = gson.fromJson(json, Post[].class);

                for (Post post : posts) {
                    Log.i(TAG, post.toString());

                    rb = mc.newRow();

                    rb.add("_id", post.getId());
                    rb.add("userId", post.getUserId());
                    rb.add("title", post.getTitle());
                    rb.add("body", post.getBody());
                }
            } else {
                mc = new MatrixCursor(commentColumnNames);
                comments = gson.fromJson(json, Comment[].class);

                for (Comment comment : comments) {
                    Log.i(TAG, comment.toString());

                    rb = mc.newRow();

                    rb.add("_id", comment.getId());
                    rb.add("postId", comment.getPostId());
                    rb.add("name", comment.getName());
                    rb.add("email", comment.getEmail());
                    rb.add("body", comment.getBody());
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "getAlbums: " + e.getMessage());

            e.printStackTrace();
        }

        return mc;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Contract is a public static class that is used to access data when the ContentResolver is initialized.
     * The data that can be access is column names for each possible data type, as well as the name of the content provider.
     */
    public static class Contract {
        public static final String AUTHORITY = "test.provider";
        public static final String[] ALBUM_COLUMN_NAMES = {"_id", "userId", "title"};
        public static final String[] POST_COLUMN_NAMES = {"_id", "userId", "title", "body"};
        public static final String[] COMMENT_COLUMN_NAMES = {"_id", "postId", "name", "email", "body"};
    }

}