package in.tosc.bookd.topactivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import in.tosc.bookd.credits.CreditsFragment;
import in.tosc.bookd.R;
import in.tosc.bookd.credits.DebitsFragment;
import in.tosc.bookd.ui.SlidingTabLayout;

public class CreditsActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = CreditsActivity.class.getSimpleName();
    private SlidingTabLayout mPagerSlidingTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    Toolbar toolbar;
    private String myTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

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

        mPagerSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        //mViewPager.setOffscreenPageLimit(1);

        mPagerSlidingTabLayout.setOnPageChangeListener(this);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPagerSlidingTabLayout.setDistributeEvenly(true);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerSlidingTabLayout.setViewPager(mViewPager);
        mPagerSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });


    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // nothing
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // nothing
    }

    @Override
    public void onPageSelected(int position) {


    }

    public class PagerAdapter extends FragmentPagerAdapter {


        private final String[] TITLES = { "Credits", "Debits"};


        public PagerAdapter(FragmentManager fm) {
            super(fm);

        }



        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0:
                return new CreditsFragment();
                case 1:
                return new DebitsFragment();

                default:
                    return null;


            }

        }

    }
}
