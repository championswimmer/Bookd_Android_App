package in.tosc.bookd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import in.tosc.bookd.topactivities.CreditsActivity;
import in.tosc.bookd.topactivities.LibraryActivity;
import in.tosc.bookd.topactivities.OrdersActivity;
import in.tosc.bookd.topactivities.WishlistActivity;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    Toolbar toolbar;
    private String myTitle;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        myTitle = getString(R.string.app_name);
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                toolbar.setTitle(myTitle);
                toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            }
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        //FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            default:
                break;
            case 0:
                startActivity(new Intent(MainActivity.this, LibraryActivity.class));
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                break;

            case 1:
                startActivity(new Intent(MainActivity.this, WishlistActivity.class));
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                break;

            case 2:
                startActivity(new Intent(MainActivity.this, OrdersActivity.class));
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                break;

            case 3:
                startActivity(new Intent(MainActivity.this, CreditsActivity.class));
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.navdrawer_item_library);
                break;
            case 1:
                mTitle = getString(R.string.navdrawer_item_wishlist);
                break;
            case 2:
                mTitle = getString(R.string.navdrawer_item_orders);
                break;
            case 3:
                mTitle = getString(R.string.navdrawer_item_credits);
                break;
            default:
                mTitle = getString(R.string.title_find_books);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
