package in.tosc.bookd;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;

/**
 * Created by yogesh on 1/4/15.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    String[] mString;
    ActionBarActivity mActionBarActivity;

    public NavigationDrawerAdapter(String[] mCategoryMap, ActionBarActivity actionBarActivity) {

        this.mString = mCategoryMap;
        this.mActionBarActivity = actionBarActivity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Array.getLength(mString);
    }

    @Override
    public String getItem(int pos) {
        // TODO Auto-generated method stub
        return mString[pos];
    }

    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        View rowView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActionBarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.drawer_item, null);
        } else {
            rowView = convertView;
        }

        ImageView navigationIcon = (ImageView) rowView.findViewById(R.id.icon);
        TextView navigationTitle = (TextView) rowView.findViewById(R.id.name);

        navigationTitle.setText(mString[pos]);
        //navigationTitle.setTextColor(Color.WHITE);
        navigationIcon.setBackgroundResource(R.mipmap.ic_launcher);

        String s = mString[pos];
        //TODO: Attach specific icons for different sections
        if (s.equals("Library")) {
            navigationIcon.setBackgroundResource(R.drawable.library);

        } else if (s.equals("Wishlist")) {
            navigationIcon.setBackgroundResource(R.drawable.wishlist);

        } else if (s.equals("Orders")) {
            navigationIcon.setBackgroundResource(R.drawable.orders);

        } else if (s.equals("Credits")) {
            navigationIcon.setBackgroundResource(R.drawable.credits);
        }
        return rowView;
    }
}
