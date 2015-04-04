package in.tosc.bookd.bookapi;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by championswimmer on 4/4/15.
 */
public class GetBookInfo extends AsyncTask<String, Void, BookObject> {

    //TODO: This AsyncTask takes a ISBN and gets back a BookObject

    Context context;

    public GetBookInfo(Context context) {
        this.context = context;
    }

    @Override
    protected BookObject doInBackground(String... strings) {
        return new BookObject();
    }

}
