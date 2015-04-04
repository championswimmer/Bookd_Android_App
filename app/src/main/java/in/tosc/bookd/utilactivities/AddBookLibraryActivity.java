package in.tosc.bookd.utilactivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parse.ParseUser;

import java.util.concurrent.ExecutionException;

import in.tosc.bookd.ParseTables;
import in.tosc.bookd.R;
import in.tosc.bookd.Utils;
import in.tosc.bookd.bookapi.BookObject;
import in.tosc.bookd.bookapi.GetBookInfo;

public class AddBookLibraryActivity extends ActionBarActivity {

    public static final String TAG = "AddBookLibraryActivity";

    public static final int ADD_BOOK_LIBRARY_RESULT = 10;

    BookObject bookObject;

    SimpleDraweeView imageBook;
    TextView tvBookTitle, tvBookAuthor, tvBookPublisher, tvBookSummary;
    Button addLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_library);

        imageBook = (SimpleDraweeView) findViewById(R.id.image_book);
        tvBookTitle = (TextView) findViewById(R.id.tv_book_title);
        tvBookAuthor = (TextView) findViewById(R.id.tv_book_author);
        tvBookPublisher = (TextView) findViewById(R.id.tv_book_publisher);
        tvBookSummary = (TextView) findViewById(R.id.tv_book_summary);
        addLibrary = (Button) findViewById(R.id.btn_addlibrary);

        Intent scan = new Intent(this, ScannerActivity.class);
        startActivityForResult(scan, ADD_BOOK_LIBRARY_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ADD_BOOK_LIBRARY_RESULT) {
            if (Utils.LOG_V) Log.v(TAG, "result returned from scanner");

            GetBookInfo gbi = new GetBookInfo(getApplicationContext());
            try {
                bookObject = gbi.execute(data.getStringExtra("ISBN")).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            if (bookObject != null) {
                if (Utils.LOG_V) Log.v(TAG, "bookObject is returned"
                        + "title" + bookObject.getTitle()
                        + "author" + bookObject.getAuthor());
                tvBookTitle.setText(bookObject.getTitle());
                tvBookAuthor.setText(bookObject.getAuthor());
                tvBookPublisher.setText(bookObject.getPublisher());
                tvBookSummary.setText(bookObject.getSummary());
                if (bookObject.getImage().startsWith("http")) {
                    Uri bookImageUri = Uri.parse(bookObject.getImage());
                    imageBook.setImageURI(bookImageUri);
                }

                addLibrary.setVisibility(View.VISIBLE);
                addLibrary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ParseUser pUser = ParseUser.getCurrentUser();
                        pUser.add(ParseTables.Users.LIBRARY,bookObject.getIsbn());
                        pUser.saveEventually();
                    }
                });

            } else {
                //TODO: What if we do not get a result ?? Do something about that too
                if (Utils.LOG_V) Log.v(TAG, "bookObject is not returned");

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
