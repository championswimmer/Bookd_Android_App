package in.tosc.bookd.topactivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import in.tosc.bookd.ParseTables;
import in.tosc.bookd.R;
import in.tosc.bookd.utilactivities.AddBookLibraryActivity;

public class LibraryActivity extends ActionBarActivity {

    Toolbar toolbar;
    private String myTitle;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.down_up_theme);
        setContentView(R.layout.activity_library);

        myTitle = getString(R.string.app_name);
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                toolbar.setTitle(myTitle);
                toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mRecyclerView = (RecyclerView) findViewById(
                R.id.library_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        FloatingActionButton libraryFab = (FloatingActionButton) findViewById(R.id.library_fab);
        libraryFab.attachToRecyclerView(mRecyclerView);

        fetchLibrary();
        libraryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddBookLibraryActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar book_item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    private void fetchLibrary(){
        ParseUser parseUser = ParseUser.getCurrentUser();
        Log.d("Response ", String.valueOf(parseUser.getJSONArray(ParseTables.Users.LIBRARY)));
        JSONArray jArray = parseUser.getJSONArray(ParseTables.Users.LIBRARY);
        ArrayList<String> isbnList = new ArrayList<String>();
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                try {
                    isbnList.add(jArray.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        ParseQuery<ParseObject> query = new ParseQuery<>("Book");
        query.whereContainedIn("isbn", isbnList);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e==null){
                    mAdapter = new LibraryAdapter(parseObjects);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
        private List<ParseObject> mDataset;

        public LibraryAdapter(List<ParseObject> dataSet) {
            mDataset = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.author.setText(mDataset.get(i).getString(ParseTables.Book.AUTHOR));
            viewHolder.bookName.setText(mDataset.get(i).getString(ParseTables.Book.TITLE));
            viewHolder.publisher.setText(mDataset.get(i).getString(ParseTables.Book.PUBLISHER));
            Uri bookImageUri = Uri.parse(mDataset.get(i).getString(ParseTables.Book.IMAGE));
            viewHolder.bookCover.setImageURI(bookImageUri);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView bookName;
            TextView author;
            TextView publisher;
            ImageButton overflowMenu;
            SimpleDraweeView bookCover;

            public ViewHolder(CardView v) {
                super(v);
                bookName = (TextView) v.findViewById(R.id.book_name);
                author = (TextView) v.findViewById(R.id.author_name);
                publisher = (TextView) v.findViewById(R.id.publisher_name);
                overflowMenu = (ImageButton) v.findViewById(R.id.overflow_menu);
                bookCover = (SimpleDraweeView) v.findViewById(R.id.book_cover);
            }
        }
    }
}
