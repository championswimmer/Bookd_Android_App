package in.tosc.bookd.utilactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.concurrent.ExecutionException;

import in.tosc.bookd.ParseTables;
import in.tosc.bookd.R;
import in.tosc.bookd.bookapi.BookObject;
import in.tosc.bookd.bookapi.GetBookInfo;

public class AddBookWishlistActivity extends ActionBarActivity {

    public static final int ADD_BOOK_WISHLIST_RESULT = 11;

    BookObject bookObject;

    ImageView imageBook;
    TextView tvBookTitle, tvBookAuthor, tvBookPublisher, tvBookSummary;
    Button addWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_wishlist);

        imageBook = (ImageView) findViewById(R.id.image_book);
        tvBookTitle = (TextView) findViewById(R.id.tv_book_title);
        tvBookAuthor = (TextView) findViewById(R.id.tv_book_author);
        tvBookPublisher = (TextView) findViewById(R.id.tv_book_publisher);
        tvBookSummary = (TextView) findViewById(R.id.tv_book_summary);
        addWishlist = (Button) findViewById(R.id.btn_addwishlist);

        Intent scan = new Intent(this, ScannerActivity.class);
        startActivityForResult(scan, ADD_BOOK_WISHLIST_RESULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ADD_BOOK_WISHLIST_RESULT) {

            GetBookInfo gbi = new GetBookInfo(getApplicationContext());
            try {
                bookObject = gbi.execute(data.getStringExtra("ISBN")).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            if (bookObject != null) {
                tvBookTitle.setText(bookObject.getTitle());
                tvBookAuthor.setText(bookObject.getAuthor());
                tvBookPublisher.setText(bookObject.getPublisher());
                tvBookSummary.setText(bookObject.getSummary());
                addWishlist.setVisibility(View.VISIBLE);
                addWishlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ParseUser pUser = ParseUser.getCurrentUser();
                        pUser.add(ParseTables.Users.WISHLIST,bookObject.getIsbn());
                        pUser.saveEventually();
                    }
                });
                //TODO: Also need to set the book image
            }
            //TODO: What if we do not get a result ?? Do something about that too

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book_wishlist, menu);
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
