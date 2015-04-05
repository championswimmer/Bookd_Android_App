package in.tosc.bookd.topactivities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import in.tosc.bookd.R;
import in.tosc.bookd.Utils;

public class ProfileActivity extends ActionBarActivity {

    Toolbar toolbar;
    private String myTitle;
    SimpleDraweeView cover,profile;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myTitle = getString(R.string.navdrawer_item_profile);
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
        cover=(SimpleDraweeView) findViewById(R.id.cover);
        profile=(SimpleDraweeView) findViewById(R.id.profile);
        name=(TextView) findViewById(R.id.name);
        name.setText(Utils.readFileAsString("name"));
        cover.setImageURI(Uri.parse(Utils.readFileAsString("cover")));
        profile.setImageURI(Uri.parse(Utils.readFileAsString("profile")));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
}
