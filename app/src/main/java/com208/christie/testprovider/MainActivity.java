package com208.christie.testprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity: The MainActivity Class extends the AppCompatActivity Class. This class will contain logic that
 * will connect it to the activity_main.xml, allowing us to write Java code to interact with an android phone interface.
 */
public class MainActivity extends AppCompatActivity {
    final String TAG = "--";
    ListView lv;
    Button albumBtn;
    Button postsBtn;
    Button commentsBtn;
    List<Album> albumList;
    List<Post> postList;
    List<Comment> commentList;
    Handler handler;

    /**
     * onCreate: onCreate is a function that is executed when the android app is opened. This function will set the layout,
     * which in this case is activity_main.xml, allowing us to update the android user interface. This function will also
     * assign all of the variables which includes ListViews, Buttons, etc found in the activity_main.xml by a resource id.
     *
     * @param savedInstanceState: savedInstanceState is a parameter of type Bundle. This parameter will hold information in the
     *                            event that the app is closed and wished to be re-opened in the same state it was upon closing.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        lv = findViewById(R.id.listAlbum);

        albumBtn = findViewById(R.id.albumBtn);
        postsBtn = findViewById(R.id.postsBtn);
        commentsBtn = findViewById(R.id.commentsBtn);

        albumBtn.setOnClickListener(btnListener);
        postsBtn.setOnClickListener(btnListener);
        commentsBtn.setOnClickListener(btnListener);
    }

    /**
     * btnListener: btnListener is a type of Button.OnClickListener. btnListener will determine which of the three buttons were
     * pressed, either Albums, Posts, or Comments, and will then call the getQuery function with the appropriate parameter.
     */
    Button.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (albumBtn.isPressed()) {
                getQuery("albums");
            } else if (postsBtn.isPressed()) {
                getQuery("posts");
            } else if (commentsBtn.isPressed()) {
                getQuery("comments");
            }
        }
    };

    /**
     * getQuery: getQuery is a function that will primarily be run on a background thread. The function will send a query to
     * the Content Provider with the specified path provided by whichever button was pressed and will populate an ArrayList
     * of the specified type with data returned from the JSONPlaceholder API.
     *
     * @param path: path is a String variable that will contain the path that will be used in the JSONPlaceholder API.
     */
    public void getQuery(String path) {
        Thread thread;

        Runnable query = () ->
        {
            Uri.Builder builder = new Uri.Builder();
            Uri uri;
            Cursor cursor;
            ContentResolver contentResolver = getContentResolver();
            Album album;
            Post post;
            Comment comment;

            builder.scheme("content").authority("test.provider").path(path);
            uri = builder.build();

            Log.i(TAG, "onCreate: " + uri.toString());

            cursor = contentResolver.query(uri, null, null, null, null, null);

            Log.i(TAG, "onCreate: query returned");

            if (cursor == null) {
                Log.i(TAG, "onCreate: cursor is null");
            } else {
                if (path == "albums") {
                    albumList = new ArrayList<>();

                    while (cursor.moveToNext()) {
                        Log.i(TAG, "onCreate: " + cursor.getInt(0) + " " + cursor.getInt(1) + " " + cursor.getString(2));

                        album = new Album();

                        album.setId(cursor.getInt(0));
                        album.setUserId(cursor.getInt(1));
                        album.setTitle(cursor.getString(2));

                        albumList.add(album);
                    }
                } else if (path == "posts") {
                    postList = new ArrayList<>();

                    while (cursor.moveToNext()) {
                        Log.i(TAG, "onCreate: " + cursor.getInt(0) + " " + cursor.getInt(1) + " " + cursor.getString(2) + " " + cursor.getString(3));

                        post = new Post();

                        post.setId(cursor.getInt(0));
                        post.setUserId(cursor.getInt(1));
                        post.setTitle(cursor.getString(2));
                        post.setBody(cursor.getString(3));

                        postList.add(post);
                    }
                } else {
                    commentList = new ArrayList<>();

                    while (cursor.moveToNext()) {
                        Log.i(TAG, "onCreate: " + cursor.getInt(0) + " " + cursor.getInt(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4));

                        comment = new Comment();

                        comment.setId(cursor.getInt(0));
                        comment.setPostId(cursor.getInt(1));
                        comment.setName(cursor.getString(2));
                        comment.setEmail(cursor.getString(3));
                        comment.setBody(cursor.getString(4));

                        commentList.add(comment);
                    }
                }

                /*
                 * Should always close the cursor when you're finished with it.
                 */

                cursor.close();

                switch (path) {
                    case "albums":
                        handler.post(updateAlbumUI);
                        break;
                    case "posts":
                        handler.post(updatePostUI);
                        break;
                    case "comments":
                        handler.post(updateCommentUI);
                        break;
                }
            }
        };

        thread = new Thread(query);
        thread.start();
    }

    /**
     * updateUI Runnables: The updateUI Runnables will be called by the handler after the JSON data has been transferred into either the Album, Post
     * or Comment class and will convert the array of the same class into list form and update the user interface.
     */
    Runnable updateAlbumUI = () ->
    {
        ArrayAdapter<Album> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, albumList);
        lv.setAdapter(arrayAdapter);
    };

    Runnable updatePostUI = () ->
    {
        ArrayAdapter<Post> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, postList);
        lv.setAdapter(arrayAdapter);
    };

    Runnable updateCommentUI = () ->
    {
        ArrayAdapter<Comment> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, commentList);
        lv.setAdapter(arrayAdapter);
    };
}